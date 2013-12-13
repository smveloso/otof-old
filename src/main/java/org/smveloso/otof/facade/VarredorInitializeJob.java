package org.smveloso.otof.facade;

/**
 *
 * @author sergiomv
 */
public class VarredorInitializeJob implements Job {

    private WorkerDelegate workerDelegate = null;
    
    private Varredor varredor = null;

    public Varredor getVarredor() {
        return varredor;
    }

    public void setVarredor(Varredor varredor) {
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
