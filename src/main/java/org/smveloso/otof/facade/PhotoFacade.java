package org.smveloso.otof.facade;

import org.smveloso.otof.ops.AlbumUpdater;
import org.smveloso.otof.gui.job.AlbumUpdaterBatchJob;
import org.smveloso.otof.gui.job.AlbumUpdaterInitializeJob;
import org.smveloso.otof.gui.job.JobSwingWorker;
import org.smveloso.otof.gui.job.BatchJobSwingWorker;
import java.io.File;
import java.util.concurrent.ExecutionException;
import org.smveloso.otof.em.PhotoJpaController;
import org.smveloso.otof.em.JpaManager;
import org.smveloso.otof.gui.job.WaitWindowWorkerDialog;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.ops.LocalFileSystemAlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class PhotoFacade {

    private PhotoJpaController fotoJpaController;

    private PhotoFacade() {
        fotoJpaController = new PhotoJpaController(JpaManager.getInstance().getFactory());
    }
  
    private static PhotoFacade instance = null;
    
    public static synchronized PhotoFacade getInstance() {
        if (null == instance) {
            instance = new PhotoFacade();
        }
        return instance;
    }

    // TODO ARGH ! I AM HARD-CODED !!!!
    public synchronized void performAlbumUpdate(LocalFileSystemAlbum album) throws FacadeException {
        
        try {
        
            //TODO adaptar para rodar com um 'progress bar' visual

            // TODO ARGH ! I AM HARD-CODED !!!!
            AlbumUpdater varredor = new LocalFileSystemAlbumUpdater(album);
            varredor.setPhotoJpaController(fotoJpaController);
            
            AlbumUpdaterInitializeJob initJob = new AlbumUpdaterInitializeJob();
            initJob.setVarredor(varredor);
            
            JobSwingWorker<Void,Void> jobWorker = new JobSwingWorker<>();
            jobWorker.setJob(initJob);
            WaitWindowWorkerDialog workerDialogForInit = new WaitWindowWorkerDialog(null, jobWorker, false, true);  // displays messages but no progress
            workerDialogForInit.setVisible(true);
            
            jobWorker.get(); // waits ...
            
            AlbumUpdaterBatchJob batchJob = new AlbumUpdaterBatchJob();
            batchJob.setVarredor(varredor);
            
            BatchJobSwingWorker<Void,Void> batchJobWorker = new BatchJobSwingWorker<>();
            batchJobWorker.setBatchJob(batchJob);
            
            WaitWindowWorkerDialog workerDialog = new WaitWindowWorkerDialog(null, batchJobWorker);
            workerDialog.setVisible(true);
            
            batchJobWorker.get(); // waits ...
            
             
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
        
    public int getTotalPhotoCount() {
        return fotoJpaController.getTotalPhotoCount();
    }
    
}
