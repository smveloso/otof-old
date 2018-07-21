package org.smveloso.otof.gui.job;

import org.smveloso.otof.service.AlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class AlbumUpdaterInitializeJob extends Job {
    
    private AlbumUpdater albumUpdater = null;

    public AlbumUpdater getAlbumUpdater() {
        return albumUpdater;
    }

    public void setAlbumUpdater(AlbumUpdater albumUpdater) {
        this.albumUpdater = albumUpdater;
    }
    
    @Override
    public void run() {
        String msgUm = "Analyzing base directory ...";
        String msgDois = "Done";

        setProgresso(0);
        setMensagem(msgUm);
        
        try {
            this.albumUpdater.initialize();
            setProgresso(100);
            setMensagem(msgDois);
        } catch (Throwable t) {
            throw new RuntimeException("ARG!",t);
        }
    }
    
}