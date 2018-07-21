package org.smveloso.otof.gui.swingworker;

import org.smveloso.otof.gui.swingworker.DisplayMessageSwingWorker;
import org.smveloso.otof.gui.job.WorkerDelegate;
import org.smveloso.otof.gui.job.DefaultWorkerDelegate;
import org.smveloso.otof.gui.job.DefaultWorkerDelegate;
import org.smveloso.otof.gui.job.Job;
import org.smveloso.otof.gui.job.WorkerDelegate;

/**
 *
 * @author sergiomv
 */
public class JobDisplayMessageSwingWorker<T,V> extends DisplayMessageSwingWorker<T, V>{

    private Job job;
    private WorkerDelegate workerDelegate;
    
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
        this.workerDelegate = new DefaultWorkerDelegate();
        this.workerDelegate.setWorker(this);
    }
    
    @Override
    protected T doInBackground() throws Exception {
        this.job.execute();        
        return null; // TODO improve ...
    }

  
}
