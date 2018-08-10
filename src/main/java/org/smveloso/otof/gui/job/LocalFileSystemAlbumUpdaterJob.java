package org.smveloso.otof.gui.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.service.LocalFileSystemAlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class LocalFileSystemAlbumUpdaterJob extends Job {
    
    private static final Logger logger = LoggerFactory.getLogger(LocalFileSystemAlbumUpdaterJob.class);
    
    private LocalFileSystemAlbumUpdater albumUpdater = null;

    public LocalFileSystemAlbumUpdater getAlbumUpdater() {
        return albumUpdater;
    }

    public void setAlbumUpdater(LocalFileSystemAlbumUpdater albumUpdater) {
        this.albumUpdater = albumUpdater;
    }
    
    @Override
    public void run() {

        setProgresso(0);
        setMensagem("Analisando diretório: " + albumUpdater.getFileSystemAlbum().getMountPoint());
        
        try {
            this.albumUpdater.initialize();
            
            int total = albumUpdater.getNumberOfFilesToProcess();

            if (total > 0) {
            
                int processed = 0;

                do {
                    albumUpdater.processNextFile();
                    processed++;
                    setMensagem("Processado " + processed + " de um total de " + total);
                } while (albumUpdater.hasMoreFiles());
            
            }
            
        } catch (Throwable t) {
            logger.error("Caught Throwable: " + t.getClass().getName());
            logger.error("MSG: " + t.getMessage());
            logger.error("STACKTRACE",t);
            throw new RuntimeException(t.getMessage(),t);
        }
    }
    
}