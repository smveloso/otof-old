package org.smveloso.otof.gui.job;

import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.ops.AlbumUpdater;
import org.smveloso.otof.gui.job.Job;
import org.smveloso.otof.gui.job.WorkerDelegate;

/**
 *
 * @author sergiomv
 */
public class AlbumUpdaterInitializeJob implements Job {

    private WorkerDelegate workerDelegate = null;
    
    private AlbumUpdater varredor = null;

    public AlbumUpdater getVarredor() {
        return varredor;
    }

    public void setVarredor(AlbumUpdater varredor) {
        this.varredor = varredor;
    }
    
    @Override
    public void execute() throws FacadeException {
        this.workerDelegate.setProgress(0);
        this.workerDelegate.setMensagem("Analyzing base directory ...");
        this.varredor.initialize();
        this.workerDelegate.setMensagem("Done");
        this.workerDelegate.setProgress(100);
    }

    @Override
    public void setWorkerDelegate(WorkerDelegate workerDelegate) {
        this.workerDelegate = workerDelegate;
    }

    
    
}
