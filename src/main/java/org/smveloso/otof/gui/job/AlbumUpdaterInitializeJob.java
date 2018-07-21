package org.smveloso.otof.gui.job;

import org.smveloso.otof.ops.AlbumUpdater;

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

        // TODO should be inside a setter method !!!!
        pcs.firePropertyChange("progress", 0, 0);
        //TODO restore it
        //pcs.firePropertyChange("mensagem","",msgUm);

        //TODO remove this

        try {        
                Thread.currentThread().sleep(1000);
                pcs.firePropertyChange("mensagem","","pass 1");
                Thread.currentThread().sleep(1000);
                pcs.firePropertyChange("mensagem","pass 1","pass 2");
                Thread.currentThread().sleep(1000);
                pcs.firePropertyChange("mensagem","pass 2",msgUm);
        } catch (Throwable t) {
                throw new RuntimeException("BOOM!",t);
        }
        
        
        try {
            this.albumUpdater.initialize();
        } catch (Throwable t) {
            throw new RuntimeException("ARG!",t);
        }
        pcs.firePropertyChange("mensagem",msgUm,msgDois);
        pcs.firePropertyChange("progress", 0, 100);
    }
    
}