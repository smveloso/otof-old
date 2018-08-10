package org.smveloso.otof.service;

import java.io.File;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.em.AlbumDAO;
import org.smveloso.otof.em.LocationDAO;
import org.smveloso.otof.util.digest.DigestUtilException;
import org.smveloso.otof.util.digest.DigestUtil;
import org.smveloso.otof.em.exception.EmException;
import org.smveloso.otof.em.PhotoDAO;
import org.smveloso.otof.em.exception.NonexistentEntityException;
import org.smveloso.otof.util.jpeg.JpegUtil;
import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;

/**
 *
 * @author sergiomv
 */
public abstract class AlbumUpdater {

    private static final Logger logger = LoggerFactory.getLogger(AlbumUpdater.class);
    
    private PhotoDAO photoDAO;
    private AlbumDAO albumDAO;
    
    protected final Album album;

    private File lastProcessedFile;
    private Iterator<File> iterator;
    private int remainingFiles;
    
    public AlbumUpdater(Album album) {
        this.album = album;
    }
    
    public void initialize() throws FacadeException {      
        logger.debug(">> initialize()");
        actualInitialization();
        this.lastProcessedFile = null;
        this.iterator = getFileIterator();
        this.remainingFiles = getNumberOfFilesToProcess();
        logger.debug("<< initialize()");
    }

    public Album getAlbum() {
        return this.album;
    }
    
    /** How many files are there to process 
     *  during the creation/updating of the
     *  album ?
     * 
     * @return 
     */    
    public abstract int getNumberOfFilesToProcess();

    /** Implementations must provide an Iterator for Files
     *  
     * @return 
     */
    public abstract Iterator<File> getFileIterator();

    /** Implementations must use this method to 
     *  prepare to provide the number of files 
     *  to process and an Iterator for accessing them.
     * 
     */
    protected abstract void actualInitialization();
    
    public void setPhotoDAO(PhotoDAO photoDAO) {
        this.photoDAO = photoDAO;
    }
    
    public void setAlbumDAO(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }
    
    public File getLastProcessedFile() {
        return this.lastProcessedFile;
    }
    
    public int getRemainingFiles() {
        return remainingFiles;
    }

    public boolean hasMoreFiles() {
        return this.iterator.hasNext();
    }
    
    public void processNextFile() throws FacadeException {

        logger.debug(">> processNextFile()");
        
        try {
        
            if (this.iterator.hasNext()) {
                this.lastProcessedFile = this.iterator.next();
                this.remainingFiles--;

                logger.debug("file: " + this.lastProcessedFile.getName());
                logger.trace("path: " + this.lastProcessedFile.getAbsolutePath());

                String digest = DigestUtil.getSha1HexEncoded(this.lastProcessedFile);

                logger.trace("sha1: " + digest);
                
                Photo photo = photoDAO.findFotoByDigest(digest);
                
                if (null == photo) {
                    // this photo has never been seen by otof
                    logger.debug("new photo");
                    photo = new Photo();
                    photo.setFileDigest(digest);
                    photo.setDateTaken(JpegUtil.safeGetDataTirada(this.lastProcessedFile));                                        
                    photo.setFileSize(this.lastProcessedFile.length());
                    photoDAO.create(photo);                    
                } else {
                    logger.trace("i've seen this photo before");
                }

                String path = this.lastProcessedFile.getPath();
                Location location = LocationDAO.getInstance().findLocationInAlbumByPath(album, path);
                
                if (null == location) {
                    // this photo is not associated to this album in any path
                    // go ahead and associate it
                    logger.trace("photo NOT associated to album");
                    location = new Location();
                    location.setAlbum(album);
                    location.setPhoto(photo);
                    location.setPath(path);
                    LocationDAO.getInstance().create(location);
                } else {
                    String existingDigest = location.getPhoto().getFileDigest();
                    logger.trace("existing digest: " + existingDigest);
                    if (existingDigest.equals(digest)) {
                        // an identical photo is already associated to the album
                        // in that particular path. nothing to do. log it ?
                        logger.trace("photo ALREADY associated to album");
                    } else {
                        // there is a photo associated to the album
                        // at this path, but it is a different photo                        
                        // update it
                        logger.info("uptading location");
                        location.setPhoto(photo);
                        LocationDAO.getInstance().update(location);
                    }
                }
                
            } else {
                throw new FacadeException("Illegal state: nothing to process.");
            }
        
        } catch (DigestUtilException | EmException | NonexistentEntityException e) {
            throw new FacadeException(e);
        } catch (Exception e) {
            System.out.println("UNEXPECTED ERROR: " + e.getMessage());
            throw new FacadeException("UNEXPECTED",e);
        }

        logger.debug("<< processNextFile()");

    }
    
    
}
