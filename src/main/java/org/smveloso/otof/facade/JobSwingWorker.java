package org.smveloso.otof.facade;

/**
 *
 * @author sergiomv
 */
public class JobSwingWorker<T,V> extends MensagemSwingWorker<T, V>{

    private Job job;
    private WorkerDelegate workerDelegate;
    
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
        this.workerDelegate = new DefaultWorkerDelegate();
        this.workerDelegate.setWorker(this);
        this.job.setWorkerDelegate(workerDelegate);
    }
    
    @Override
    protected T doInBackground() throws Exception {
        this.job.execute();        
        return null; // TODO improve ...
    }

  
}
