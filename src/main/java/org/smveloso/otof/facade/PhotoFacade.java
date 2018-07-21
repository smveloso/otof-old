package org.smveloso.otof.facade;

import org.smveloso.otof.ops.AlbumUpdater;
import org.smveloso.otof.gui.job.AlbumUpdaterInitializeJob;
import java.util.concurrent.ExecutionException;
import org.smveloso.otof.em.PhotoDAO;
import org.smveloso.otof.gui.swingworker.DisplayMessageSwingWorker;
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

    public synchronized void performAlbumUpdate(LocalFileSystemAlbum album) throws FacadeException {
        
        try {

            AlbumUpdater albumUpdater = new LocalFileSystemAlbumUpdater(album);
            albumUpdater.setPhotoJpaController(photoDAO);
            AlbumUpdaterInitializeJob initJob = new AlbumUpdaterInitializeJob();
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
