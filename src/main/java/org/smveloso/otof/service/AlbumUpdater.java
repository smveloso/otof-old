package org.smveloso.otof.service;

import java.io.File;
import java.util.Iterator;
import org.smveloso.otof.util.digest.DigestUtilException;
import org.smveloso.otof.util.digest.DigestUtil;
import org.smveloso.otof.em.exception.EmException;
import org.smveloso.otof.em.PhotoDAO;
import org.smveloso.otof.util.jpeg.JpegUtil;
import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Photo;

/**
 *
 * @author sergiomv
 */
public abstract class AlbumUpdater {

    private PhotoDAO photoJpaController;
    
    protected final Album album;

    private File lastProcessedFile;
    private Iterator<File> iterator;
    private int remainingFiles;
    
    public AlbumUpdater(Album album) {
        this.album = album;
    }
    
    public void initialize() throws FacadeException {
        actualInitialization();
        this.lastProcessedFile = null;
        this.iterator = getFileIterator();
        this.remainingFiles = getNumberOfFilesToProcess();        
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
    
    public void setPhotoJpaController(PhotoDAO photoJpaController) {
        this.photoJpaController = photoJpaController;
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

        try {
        
            if (this.iterator.hasNext()) {
                this.lastProcessedFile = this.iterator.next();
                this.remainingFiles--;
                
                String digest = DigestUtil.getSha1HexEncoded(this.lastProcessedFile);

                Photo alreadySeenPhoto = photoJpaController.findFotoByDigest(digest);
                if (null == alreadySeenPhoto) {

                    Photo newPhoto = new Photo();
                    newPhoto.setFileDigest(digest);
                    newPhoto.setDateTaken(JpegUtil.safeGetDataTirada(this.lastProcessedFile));                                        
                    newPhoto.setFileSize(this.lastProcessedFile.length());
                    photoJpaController.create(newPhoto);

                    // localtiondao.registraFotoEmAlbum(getAlbum(),newFoto)
                    
                } else {
                 
                    // foi localizada uma copia de foto conhecida pelo otof
                    
                    // sera que o album em atualizacao ja a registra ?
                    
                    // done = albumdao.isFotoInAlbum(getAlbum(),alreadySeenPhoto)

                    // not done ?
                    // localtiondao.registraFotoEmAlbum(getAlbum(),alreadySeenPhoto)

                    // done !
                    // maybe check if the photo is the same (by digest)
                    //   -- update if not
                    
                }
                
            } else {
                throw new FacadeException("Illegal state: nothing to process.");
            }
        
        } catch (DigestUtilException | EmException e) {
            throw new FacadeException(e);
        }
        
    }
    
    
}
