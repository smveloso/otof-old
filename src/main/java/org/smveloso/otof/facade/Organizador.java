package org.smveloso.otof.facade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.model.Foto;

/**
 *
 * @author sergiomv
 */
public class Organizador {

    public static final String DEFAULT_UNIT_NAME_PREFIX = "DVD";
    
    // <unit name, contents>
    //private Map<String, List<Foto>> catalogo = new HashMap<String,List<Foto>>();
    private List<Unidade> unidades = new ArrayList<Unidade>();
    
    private List<Foto> todasAsFotos = null;
    
    private FotoJpaController fotoJpaController;

    // this one has it´s own worker delegate ...
    private WorkerDelegate workerDelegate;
    
    private boolean createManifest;
    private boolean createCopies;
    
    boolean processEverything = false;
    
    private File manifest = null;
    private File destDir = null;   
    
    private long bytesPerUnit;

    public FotoJpaController getFotoJpaController() {
        return fotoJpaController;
    }

    public void setFotoJpaController(FotoJpaController fotoJpaController) {
        this.fotoJpaController = fotoJpaController;
    }

    public boolean isCreateManifest() {
        return createManifest;
    }

    public void setCreateManifest(boolean createManifest) {
        this.createManifest = createManifest;
    }

    public boolean isCreateCopies() {
        return createCopies;
    }

    public void setCreateCopies(boolean createCopies) {
        this.createCopies = createCopies;
    }

    public boolean isProcessEverything() {
        return processEverything;
    }

    public void setProcessEverything(boolean processEverything) {
        this.processEverything = processEverything;
    }

    public File getManifest() {
        return manifest;
    }

    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

    public File getDestDir() {
        return destDir;
    }

    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    public long getBytesPerUnit() {
        return bytesPerUnit;
    }

    public void setBytesPerUnit(long bytesPerUnit) {
        this.bytesPerUnit = bytesPerUnit;
    }

    public void setWorkerDelegate(WorkerDelegate workerDelegate) {
        this.workerDelegate = workerDelegate;
    }

    public synchronized void organize() throws FacadeException {
        
        if (!validateOptions()) {
            throw new FacadeException("Opções incorretas para organização.");
        }
        
        if (!isProcessEverything()) {
            throw new FacadeException("Opções incorretas para organização: somente processo tudo.");
        }

        safeSetProgress(0);
        safeSetMensagem("Montando lista de fotos a partir do banco de dados ...");
        
        todasAsFotos = fotoJpaController.findFotoEntities();

        if ((null == todasAsFotos) || (todasAsFotos.isEmpty())) {
            safeSetProgress(100);
            safeSetMensagem("Nenhuma foto para processar!");
            return;
        }
        
        safeSetMensagem("Obtida lista com " + todasAsFotos.size() + " fotos no total. Organizando unidades ...");

        ordenarPorDataTirada();
        
        organizarEmUnidades();
        
        if (isCreateManifest()) {
            criarManifesto();
        }
        
        if (isCreateCopies()) {
            criarCopias();
        }
        
    }
    
    private void ordenarPorDataTirada() {
        
        Comparator<Foto> comparator = new Comparator<Foto>() {

            @Override
            public int compare(Foto o1, Foto o2) {
                
                if (o1.equals(o2)) {
                    return 0;
                }
                
                if ((o1.getDataTirada() == null) && (o2.getDataTirada() == null)) {
                    return 0;
                }

                if (o1.getDataTirada() == null) {
                    return -1;
                }
                
                if (o2.getDataTirada() == null) {
                    return 1;
                }

                return o1.getDataTirada().compareTo(o2.getDataTirada());
            
            }
            
        };
        
        Collections.sort(todasAsFotos, comparator);
        
    }

    private void organizarEmUnidades() {
        
        long maxUnitSize = this.bytesPerUnit;
        
        int unitCounter = 1;
        long unitSizeNow = 0;

        Unidade unidade = new Unidade(DEFAULT_UNIT_NAME_PREFIX + String.valueOf(unitCounter));
        List<Foto> fotosNaUnidade = new ArrayList<>();
        unidade.setFotos(fotosNaUnidade);
        unidades.add(unidade);
        
        for (Foto foto:todasAsFotos) {

            long increase = foto.getTamanhoArquivo();
            
            if ((unitSizeNow + increase) >= maxUnitSize) {
                unitCounter++;
                unitSizeNow = 0;
                unidade = new Unidade(DEFAULT_UNIT_NAME_PREFIX + String.valueOf(unitCounter));
                fotosNaUnidade = new ArrayList<>();
                unidade.setFotos(fotosNaUnidade);
                unidades.add(unidade);
            }

            fotosNaUnidade.add(foto);
            unitSizeNow += increase;
            
        }
    }
    
    private void criarManifesto() throws FacadeException {
        if (isCreateManifest()) {
            safeSetMensagem("Criando manifesto em: " + getManifest().getName());

            FileWriter fw = null;
            try {

                fw = new FileWriter(getManifest());
                for (int k = 0; k < unidades.size(); ++k) {
                    fw.write("Unidade nro: " + (k+1) + ". Nome: " + unidades.get(k).getNome() + "\n");
                    for (Foto foto:unidades.get(k).getFotos()) {
                        
                        if (foto.getDataTirada() != null) {
                            fw.write(String.format("%1$s\t%2$d\t%3$Td/%3$Tm/%3$TY %3$TH:%3$TM:%3$TS\t%4$s\n", 
                                                    foto.getArquivo(), 
                                                    foto.getTamanhoArquivo(), 
                                                    foto.getDataTirada(),
                                                    foto.getDigest()));
                        } else {
                            fw.write(String.format("%1$s\t%2$d\t%3$s\t%4$s\n", 
                                                    foto.getArquivo(), 
                                                    foto.getTamanhoArquivo(), 
                                                    "sem data",
                                                    foto.getDigest()));                            
                        }
                    }
                }

                fw.flush();
                
            } catch (IOException e) {
                throw new FacadeException("Erro ao criar manifesto: " + e.getMessage());
            } finally {
                if (null != fw) {
                    try {
                        fw.close();
                    } catch (IOException ignored) {
                    }
                }
            }

        }
    }
    
    private void criarCopias() throws FacadeException {    
        if (isCreateCopies()) {
            
            if ((getDestDir() == null) || (!getDestDir().exists()) || (!getDestDir().isDirectory()) || (!getDestDir().canWrite())) {
                throw new FacadeException("Problemas para gravar no diretório de destino das cópias.");
            }
            
            int nroUnidades = unidades.size();
            for (int k = 0; k < nroUnidades; ++k) {

                safeSetMensagem("Copiando arquivos para unidade " + (k + 1) + " : ");
                Unidade unidade = unidades.get(k);

                File destDirUnidade = new File(getDestDir(),unidade.getNome());
                if (!destDirUnidade.mkdir()) {
                    throw new FacadeException("Erro ao criar diretório para unidade: " + destDirUnidade.getAbsolutePath());
                }
                
                for (Foto foto:unidade.getFotos()) {
                    
                    try {
                        String fullOrgFileName = foto.getArquivo();
                        String destFileName = extractNameFromPath(fullOrgFileName);
                        File destFile = new File(destDirUnidade,destFileName); // a principio tera o mesmo nome da origem
                        int x = 0;
                        while(destFile.exists()) {
                            // colisao no nome do arquivo no diretorio de destino
                            destFile = new File(destDirUnidade, destFileName + ".copia." + (++x));
                        }
                        FileUtils.copyFile(new File(fullOrgFileName), destFile, true);
                    } catch (IOException e) {
                        throw new FacadeException("Erro ao copiar arquivo: " + foto.getArquivo(),e);
                    }
                    
                } // iteracao na lista de fotos de uma unidade
                
            } // iteracao na lista de unidades

        }
    }
    
    private void safeSetMensagem(String msg) {
        if (this.workerDelegate != null) {
            this.workerDelegate.setMensagem(msg);
        }
    }

    private void safeSetProgress(int progress) {
        if (this.workerDelegate != null) {
            this.workerDelegate.setProgress(progress);
        }
    }
    
    private boolean validateOptions() {        
        //TODO improve me
        boolean ok = true;
        return ok;
    }
        
    protected String extractNameFromPath(String path) {
        String name = null;
        int lastSeparator = path.lastIndexOf(File.separator);
        
        if (lastSeparator == -1) {
            name = path;
        } else {
            //TODO handle the case when the path ends with a backslash
            name = path.substring((lastSeparator + 1));
        }
        return name;
    }
    
    
}

//TODO vai virar uma entidade 
class Unidade {
    
    private String nome;
    private List<Foto> fotos;

    public Unidade() {
    }

    public Unidade(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
    
}
