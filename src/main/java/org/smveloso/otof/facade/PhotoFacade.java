package org.smveloso.otof.facade;

import org.smveloso.otof.ops.AlbumUpdater;
import org.smveloso.otof.gui.job.AlbumUpdaterBatchJob;
import org.smveloso.otof.gui.job.AlbumUpdaterInitializeJob;
import org.smveloso.otof.gui.swingworker.JobDisplayMessageSwingWorker;
import org.smveloso.otof.gui.swingworker.BatchJobDisplayMessageSwingWorker;
import java.util.concurrent.ExecutionException;
import org.smveloso.otof.em.PhotoDAO;
import org.smveloso.otof.gui.job.WaitWindowWorkerDialog;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.ops.LocalFileSystemAlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class PhotoFacade {

    private final PhotoDAO photoDAO;

    private PhotoFacade() {
        photoDAO = PhotoDAO.getInstance();
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
            varredor.setPhotoJpaController(photoDAO);
            
            AlbumUpdaterInitializeJob initJob = new AlbumUpdaterInitializeJob();
            initJob.setVarredor(varredor);
            
            JobDisplayMessageSwingWorker<Void,Void> jobWorker = new JobDisplayMessageSwingWorker<>();
            jobWorker.setJob(initJob);
            WaitWindowWorkerDialog workerDialogForInit = new WaitWindowWorkerDialog(null, jobWorker, false, true);  // displays messages but no progress
            workerDialogForInit.setVisible(true);
            
            jobWorker.get(); // waits ...
            
            AlbumUpdaterBatchJob batchJob = new AlbumUpdaterBatchJob();
            batchJob.setVarredor(varredor);
            
            BatchJobDisplayMessageSwingWorker<Void,Void> batchJobWorker = new BatchJobDisplayMessageSwingWorker<>();
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
        return photoDAO.getTotalPhotoCount();
    }
    
}
