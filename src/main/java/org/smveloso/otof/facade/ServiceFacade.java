package org.smveloso.otof.facade;

import org.smveloso.otof.service.AlbumUpdater;
import org.smveloso.otof.gui.job.LocalFileSystemAlbumUpdaterJob;
import java.util.concurrent.ExecutionException;
import org.smveloso.otof.em.PhotoDAO;
import org.smveloso.otof.gui.swingworker.DisplayMessageSwingWorker;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.service.LocalFileSystemAlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class ServiceFacade {

    private final PhotoDAO photoDAO;

    private ServiceFacade() {
        photoDAO = PhotoDAO.getInstance();
    }
  
    private static ServiceFacade instance = null;
    
    public static synchronized ServiceFacade getInstance() {
        if (null == instance) {
            instance = new ServiceFacade();
        }
        return instance;
    }

    public synchronized void performAlbumUpdate(LocalFileSystemAlbum album) throws FacadeException {
        
        try {

            LocalFileSystemAlbumUpdater albumUpdater = new LocalFileSystemAlbumUpdater(album);
            albumUpdater.setPhotoJpaController(photoDAO);
            LocalFileSystemAlbumUpdaterJob initJob = new LocalFileSystemAlbumUpdaterJob();
            initJob.setAlbumUpdater(albumUpdater);
            
            DisplayMessageSwingWorker<Void,Void> swingWorker = new DisplayMessageSwingWorker<>();
            swingWorker.executeJob(initJob);
            swingWorker.get();
            
        } catch (InterruptedException| ExecutionException e) {
            throw new FacadeException("Job interrupted or failed: " + e.getMessage());
        } finally {
            
        }

    }
        
    public int getTotalPhotoCount() {
        return photoDAO.getTotalPhotoCount();
    }
    
}
