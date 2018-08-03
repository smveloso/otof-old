package org.smveloso.otof.service;

import java.io.File;
import java.util.Iterator;
import org.smveloso.otof.em.AlbumDAO;
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

        try {
        
            if (this.iterator.hasNext()) {
                this.lastProcessedFile = this.iterator.next();
                this.remainingFiles--;
                
                String digest = DigestUtil.getSha1HexEncoded(this.lastProcessedFile);

                Photo photo = photoDAO.findFotoByDigest(digest);
                
                if (null == photo) {
                    photo = new Photo();
                    photo.setFileDigest(digest);
                    photo.setDateTaken(JpegUtil.safeGetDataTirada(this.lastProcessedFile));                                        
                    photo.setFileSize(this.lastProcessedFile.length());
                    photoDAO.create(photo);
                }

                // TODO checar se ha alguma foto associada ao album
                //      pelo mesmo path e ...
                //      se for a mesma foto, pular
                //      se for outra foto, remover a associacao e criar outra
                

                
            } else {
                throw new FacadeException("Illegal state: nothing to process.");
            }
        
        } catch (DigestUtilException | EmException e) {
            throw new FacadeException(e);
        }
        
    }
    
    
}
