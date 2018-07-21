package org.smveloso.otof.gui.job;

import org.smveloso.otof.gui.job.BatchJob;
import java.io.File;
import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.ops.AlbumUpdater;

/**
 *
 * @author sergiomv
 */
public class AlbumUpdaterBatchJob implements BatchJob<File> {

    private AlbumUpdater varredor;

    public AlbumUpdater getVarredor() {
        return varredor;
    }

    public void setVarredor(AlbumUpdater varredor) {
        this.varredor = varredor;
    }
    
    @Override
    public boolean hasMoreItems() {
        return varredor.hasMoreFiles();
    }

    @Override
    public int getProgress() {
        float percent = ((float) varredor.getRemainingFiles() / (float) varredor.getNumberOfFilesToProcess()) * 100;
        return 100 - Math.round(percent);
    }

    @Override
    public String getMessage() {
        if (varredor.getLastProcessedFile() != null) {
            return "Processado: " + varredor.getLastProcessedFile().getName();
        } else {
            return "";
        }
    }

    @Override
    public void process(File item) throws FacadeException {

        /*
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException ignored) {}
        */

        // ignores the argument ... should be the obj 
        // returned in the last time getNextItem was called
        varredor.processNextFile();
    }    

    @Override
    public File getLastProcessedItem() {
        return varredor.getLastProcessedFile();
    }
    
}
