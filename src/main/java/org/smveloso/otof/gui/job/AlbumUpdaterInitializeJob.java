package org.smveloso.otof.gui.job;

import net.sf.cglib.core.Local;
import org.smveloso.otof.service.AlbumUpdater;
import org.smveloso.otof.service.LocalFileSystemAlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class AlbumUpdaterInitializeJob extends Job {
    
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
            throw new RuntimeException(t.getMessage(),t);
        }
    }
    
}