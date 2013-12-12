package org.smveloso.otof.facade;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import org.apache.commons.io.FileUtils;
import org.smveloso.otof.digest.DigestException;
import org.smveloso.otof.digest.DigestFacade;
import org.smveloso.otof.em.EmException;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.em.JpaManager;
import org.smveloso.otof.exinf.ExinfException;
import org.smveloso.otof.exinf.ExinfFacade;
import org.smveloso.otof.gui.WaitWindowWorkerDialog;
import org.smveloso.otof.model.Foto;

/**
 *
 * @author sergiomv
 */
public class FotoFacade {

    private FotoJpaController fotoJpaController;

    private FotoFacade() {
        fotoJpaController = new FotoJpaController(JpaManager.getInstance().getFactory());
    }
  
    private static FotoFacade instance = null;
    
    public static synchronized FotoFacade getInstance() {
        if (null == instance) {
            instance = new FotoFacade();
        }
        return instance;
    }

    public synchronized void executaVarredura(File baseDir) throws FacadeException {
        
        try {
        
            //TODO adaptar para rodar com um 'progress bar' visual

            // sanidade
            if ((null == baseDir) || (!baseDir.exists()) || (!baseDir.isDirectory()) || (!baseDir.canRead())) {
                throw new FacadeException("Varredura não pode começar. Erro no acesso ao diretório base.");
            }

            Varredor varredor = new Varredor(baseDir);
            varredor.setFotoJpaController(fotoJpaController);
            varredor.initialize();
            
            VarredorBatchJob batchJob = new VarredorBatchJob();
            batchJob.setVarredor(varredor);
            
            BatchJobSwingWorker<Void,Void> batchJobWorker = new BatchJobSwingWorker<Void,Void>();
            batchJobWorker.setBatchJob(batchJob);
            
            WaitWindowWorkerDialog workerDialog = new WaitWindowWorkerDialog(null, batchJobWorker);
            workerDialog.setVisible(true);
            
            batchJobWorker.get();
            
             
        /*
        } catch (EmException | DigestException | ExinfException e) {
            //LOG
            //TODO roll back ???
            throw new FacadeException("Erro durante varredura:" + e.getMessage(), e);
        */  
            
        } catch (InterruptedException| ExecutionException e) {
            throw new FacadeException("Job interrupted or failed: " + e.getMessage());
        } finally {
            
        }

    }
    
}
