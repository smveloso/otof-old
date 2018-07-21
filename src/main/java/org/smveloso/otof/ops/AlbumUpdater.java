package org.smveloso.otof.ops;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.smveloso.otof.util.digest.DigestException;
import org.smveloso.otof.util.digest.DigestFacade;
import org.smveloso.otof.em.exception.EmException;
import org.smveloso.otof.em.PhotoJpaController;
import org.smveloso.otof.util.jpeg.JpegUtilException;
import org.smveloso.otof.util.jpeg.JpegUtil;
import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Photo;

/**
 *
 * @author sergiomv
 */
public abstract class AlbumUpdater {

    private PhotoJpaController photoJpaController;
    
    private final Album album;

    private File lastProcessedFile;
    private Collection<File> files;
    private Iterator<File> iterator;
    private int remainingFiles;
    
    public AlbumUpdater(Album album) {
        this.album = album;
    }

    public void initialize() throws FacadeException {

        //this.files = FileUtils.listFiles(baseDir, new String[]{"jpg", "JPG"}, true);
        actualInitialization();
        this.lastProcessedFile = null;
        this.iterator = getFileIterator();
        this.remainingFiles = getNumberOfFilesToProcess();
        
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
    
    public void setPhotoJpaController(PhotoJpaController photoJpaController) {
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
                
                String digest = DigestFacade.getSha1HexEncoded(this.lastProcessedFile);
                
                //LOG
                Photo alreadySeenPhoto = photoJpaController.findFotoByDigest(digest);
                if (null == alreadySeenPhoto) {

                    // computar digest e cadastrar
                    Photo newPhoto = new Photo();
                    // vou usar um album aqui
                    //naoExiste.setArquivo(this.lastProcessedFile.getAbsolutePath());
                    newPhoto.setFileDigest(digest);
                    
                    try {
                        newPhoto.setDateTaken(JpegUtil.getDataTirada(this.lastProcessedFile));
                    } catch (JpegUtilException noData) {
                        newPhoto.setDateTaken(null);
                    }
                    
                    // data identificacao associada ao album !!!
                    //naoExiste.setDataIdentificada(Calendar.getInstance().getTime());
                    
                    newPhoto.setFileSize(this.lastProcessedFile.length());

                    photoJpaController.create(newPhoto);

                } else {
                 
                    // foi localizada uma copia de foto conhecida pelo otof
                    
                    // sera que o album em atualizacao ja a registra ?
                    
                    // 1. verificar se o 'path' existe para o album
                    // 1.1. nao existe -> criar registro
                    // 1.2. existe -> 2
                    // 2. verificar se o 'path' corresponde aa mesma foto
                    // 2.1. sim ? -> nada a fazer
                    // 2.2. nao ? -> atualizar registro, apontando a foto certa
                    
                    
                    
                    

                    
                }
                
            } else {
                throw new FacadeException("Illegal state: nothing to process.");
            }
        
        } catch (DigestException| EmException e) {
            throw new FacadeException(e);
        }
        
    }
    
    
}
