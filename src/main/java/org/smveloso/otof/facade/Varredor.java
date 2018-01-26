package org.smveloso.otof.facade;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.smveloso.otof.digest.DigestException;
import org.smveloso.otof.digest.DigestFacade;
import org.smveloso.otof.em.EmException;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.exinf.ExinfException;
import org.smveloso.otof.exinf.ExinfFacade;
import org.smveloso.otof.model.Foto;

/**
 *
 * @author sergiomv
 */
public class Varredor {

    private FotoJpaController fotoJpaController;
    
    //TODO Album here !!!
    
    private File baseDir;
    private File lastProcessedFile;
    private Collection<File> files;
    private Iterator<File> iterator;
    private int remainingFiles;
    
    public Varredor(File baseDir) {
        this.baseDir = baseDir;
    }

    public void initialize() throws FacadeException {

        if ((null == baseDir) || (!baseDir.exists()) || (!baseDir.isDirectory()) || (!baseDir.canRead())) {
            throw new FacadeException("Varredura não pode começar. Erro no acesso ao diretório base.");
        }

        this.files = FileUtils.listFiles(baseDir, new String[]{"jpg", "JPG"}, true);
        this.iterator = this.files.iterator();
        this.lastProcessedFile = null;
        this.remainingFiles = this.files.size();
        
    }

    public void setFotoJpaController(FotoJpaController fotoJpaController) {
        this.fotoJpaController = fotoJpaController;
    }
    
    public File getLastProcessedFile() {
        return this.lastProcessedFile;
    }
    
    public int getNumberOfFiles() {
        return files.size();
    }

    public int getRemainingFiles() {
        return remainingFiles;
    }

    public boolean hasMoreFiles() {
        return this.iterator.hasNext();
    }
    
    public void processNextFile() throws FacadeException {

        try {
        
            if (this.iterator.hasNext()) {
                this.lastProcessedFile = this.iterator.next();
                this.remainingFiles--;
                
                String digest = DigestFacade.getSha1HexEncoded(this.lastProcessedFile);
                
                //LOG
                Foto jaExiste = fotoJpaController.findFotoByDigest(digest);
                if (null == jaExiste) {

                    // computar digest e cadastrar
                    Foto naoExiste = new Foto();
                    // vou usar um album aqui
                    //naoExiste.setArquivo(this.lastProcessedFile.getAbsolutePath());
                    naoExiste.setDigest(digest);
                    
                    try {
                        naoExiste.setDataTirada(ExinfFacade.getDataTirada(this.lastProcessedFile));
                    } catch (ExinfException noData) {
                        naoExiste.setDataTirada(null);
                    }
                    
                    // data identificacao associada ao album !!!
                    //naoExiste.setDataIdentificada(Calendar.getInstance().getTime());
                    
                    naoExiste.setTamanhoArquivo(this.lastProcessedFile.length());

                    fotoJpaController.create(naoExiste);

                } else {
                    
                }
                
            } else {
                throw new FacadeException("Illegal state: nothing to process.");
            }
        
        } catch (DigestException| EmException e) {
            throw new FacadeException(e);
        }
        
    }
    
    
}
