package org.smveloso.otof.facade;

import java.io.File;

/**
 *
 * @author sergiomv
 */
public class VarredorBatchJob implements BatchJob<File> {

    private Varredor varredor;

    public Varredor getVarredor() {
        return varredor;
    }

    public void setVarredor(Varredor varredor) {
        this.varredor = varredor;
    }
    
    @Override
    public boolean hasMoreItems() {
        return varredor.hasMoreFiles();
    }

    @Override
    public int getProgress() {
        float percent = ((float) varredor.getRemainingFiles() / (float) varredor.getNumberOfFiles()) * 100;
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
