package org.smveloso.otof.facade;

import org.smveloso.otof.service.AlbumUpdater;
import org.smveloso.otof.gui.job.LocalFileSystemAlbumUpdaterJob;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.em.AlbumDAO;
import org.smveloso.otof.em.PhotoDAO;
import org.smveloso.otof.em.exception.EmException;
import org.smveloso.otof.gui.swingworker.DisplayMessageSwingWorker;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.service.LocalFileSystemAlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class ServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(ServiceFacade.class);
    
    private final PhotoDAO photoDAO;
    private final AlbumDAO albumDAO;
    
    private ServiceFacade() {
        photoDAO = PhotoDAO.getInstance();
        albumDAO = AlbumDAO.getInstance();        
    }
  
    private static ServiceFacade instance = null;
    
    public static synchronized ServiceFacade getInstance() {
        if (null == instance) {
            instance = new ServiceFacade();
        }
        return instance;
    }

    /** @return O album encontrado ou null.
     */
    public Album getAlbumByName(String albumName) throws FacadeException {
        try {
            Album album = null;
            album = albumDAO.findAlbumByName(albumName);
            return album;
        } catch (EmException e) {
            throw new FacadeException(e);
        }
    }
    
    public LocalFileSystemAlbum newLocalFileSystemAlbum(String albumName, String mountPoint) throws FacadeException {
        Album album = new LocalFileSystemAlbum();
        album.setName(albumName);
        ((LocalFileSystemAlbum) album).setMountPointAsString(mountPoint);            
        albumDAO.create(album);
        return (LocalFileSystemAlbum) getAlbumByName(albumName);
    }
    
    //TODO esta tratando apenas um tipo de album
    public synchronized void performAlbumUpdate(Album album) throws FacadeException {

        logger.debug(">> performAlbumUpdate(Album)");
        
        if (album.getClass() != LocalFileSystemAlbum.class) {
            throw new IllegalArgumentException("For now I only care for local file system !");
        }
        
        try {            
            LocalFileSystemAlbumUpdater albumUpdater = new LocalFileSystemAlbumUpdater((LocalFileSystemAlbum) album);
            albumUpdater.setPhotoDAO(photoDAO);
            albumUpdater.setAlbumDAO(albumDAO);
            LocalFileSystemAlbumUpdaterJob initJob = new LocalFileSystemAlbumUpdaterJob();
            initJob.setAlbumUpdater(albumUpdater);
            
            DisplayMessageSwingWorker<Void,Void> swingWorker = new DisplayMessageSwingWorker<>();
            
            logger.debug("will call executeJob ...");
            swingWorker.executeJob(initJob);
            logger.debug("will call get() ...");
            swingWorker.get();
    
            logger.debug("<< performAlbumUpdate(Album)");
            
        } catch (InterruptedException| ExecutionException e) {
            logger.error("Job interrupted or failed: " + e.getMessage(),e);
            throw new FacadeException("Job interrupted or failed: " + e.getMessage());
        } finally {
            
        }

    }
        
    public int getTotalPhotoCount() {
        return photoDAO.getTotalPhotoCount();
    }
    
}
