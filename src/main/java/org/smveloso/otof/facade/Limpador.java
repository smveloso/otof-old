package org.smveloso.otof.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.smveloso.otof.em.EmException;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.em.NonexistentEntityException;
import org.smveloso.otof.model.Foto;

/**
 *
 * @author sergio
 */
public class Limpador {

    private OPERACAO operacao = OPERACAO.MARCAR;
    
    private FotoJpaController fotoJpaController;

    // this one has it´s own worker delegate ...
    private WorkerDelegate workerDelegate;

    public FotoJpaController getFotoJpaController() {
        return fotoJpaController;
    }

    public void setFotoJpaController(FotoJpaController fotoJpaController) {
        this.fotoJpaController = fotoJpaController;
    }

    public WorkerDelegate getWorkerDelegate() {
        return workerDelegate;
    }

    public void setWorkerDelegate(WorkerDelegate workerDelegate) {
        this.workerDelegate = workerDelegate;
    }

    public OPERACAO getOperacao() {
        return operacao;
    }

    public void setOperacao(OPERACAO operacao) {
        this.operacao = operacao;
    }
    
    public void eliminarDuplicadasPorDigest() throws FacadeException {

        try {
            
            safeSetProgress(0);
            safeSetMensagem("Listando fotos no banco de dados ...");
            
            //TODO see how to do it correctly ...
            //List<Foto> duplicadas = fotoJpaController.findDuplicadasPorDigest();
            List<Foto> todasAsFotos = fotoJpaController.findFotoEntities();
            
            Comparator<Foto> comparator = new Comparator<Foto>() {

                @Override
                public int compare(Foto o1, Foto o2) {

                    // sanidade
                    if ((o1.getDigest() == null) || (o2.getDigest() == null)) {
                        throw new IllegalArgumentException("Fotos devem ter digest não nulo.");
                    }

                    if (o1.equals(o2)) {
                        return 0;
                    }

                    return o1.getDigest().compareTo(o2.getDigest());

                }
            
            };
        
            //Collections.sort(duplicadas, comparator);
            Collections.sort(todasAsFotos, comparator);
            
            safeSetMensagem("Detectando duplicados ...");

            List<Foto> fotosMatriz = new ArrayList<>();
            List<Foto> fotosDuplicadas = new ArrayList<>();
            String digest = null;
            int counter = 0;
            int total = todasAsFotos.size();
            //for (Foto f:duplicadas) {
            for (Foto f:todasAsFotos) {                
                safeSetProgress( Math.round( ((float) (++counter) / (float) total) * (float) 100 ) );
                if (!f.getDigest().equals(digest)) {
                    digest = f.getDigest();
                    fotosMatriz.add(f);
                    continue;
                }
                fotosDuplicadas.add(f);
            }

            if (getOperacao().equals(Limpador.OPERACAO.MARCAR)) {
                safeSetMensagem("Marcando registros duplicados no banco de dados ...");
                safeSetProgress(0);
                counter = 0;
                
                for (Foto f:fotosMatriz) {
                    f.setDuplicate(false);
                    fotoJpaController.edit(f);
                } 
                
                for (Foto f:fotosDuplicadas){
                    f.setDuplicate(true);
                    fotoJpaController.edit(f);
                }
                
            } else {
                safeSetMensagem("Eliminando registros de duplicados no banco de dados ...");
            } 
            
        } catch (EmException|NonexistentEntityException e) {
            throw new FacadeException(e.getMessage(),e);
        } catch (Exception e){
            throw new FacadeException(e.getMessage(),e);
        } finally {
            
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

    public static enum OPERACAO {
        MARCAR,
        ELIMINAR_NO_BANCO;
    }
}
