package org.smveloso.otof.gui.job;

import org.smveloso.otof.gui.job.DisplayMessageSwingWorker;
import org.smveloso.otof.gui.job.WorkerDelegate;
import org.smveloso.otof.gui.job.DefaultWorkerDelegate;

/**
 *
 * @author sergiomv
 */
public class JobSwingWorker<T,V> extends DisplayMessageSwingWorker<T, V>{

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
