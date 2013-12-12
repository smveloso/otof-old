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
    public File getNextItem() {
        return varredor.getCurrentFile();
    }

    @Override
    public int getProgress() {
        return 1;
    }

    @Override
    public String getMessage() {
        return "n/a";
    }

    @Override
    public void process(File item) throws FacadeException {
        // ignores the argument ... should be the obj 
        // returned in the last time getNextItem was called
        varredor.processNextFile();
    }    
    
}
