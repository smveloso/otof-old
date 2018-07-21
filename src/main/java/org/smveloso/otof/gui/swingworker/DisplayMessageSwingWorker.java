package org.smveloso.otof.gui.swingworker;

import javax.swing.SwingWorker;
import org.smveloso.otof.gui.job.Job;
import org.smveloso.otof.gui.swingworker.dialog.WaitWindowWorkerDialog;

/**
 *
 * @author sergiomv
 */
public class DisplayMessageSwingWorker<T,V> extends SwingWorker<T, V> {
    
    protected String mensagem;

    protected Job job;
    
    protected WaitWindowWorkerDialog workerDialog;

    public DisplayMessageSwingWorker() {
        workerDialog = new WaitWindowWorkerDialog(null, this, false, true);  // displays messages but no progress
        //this.addPropertyChangeListener(workerDialog);
        workerDialog.setVisible(true);
    }

    public DisplayMessageSwingWorker(WaitWindowWorkerDialog workerDialog) {
        this.workerDialog = workerDialog;
        //this.addPropertyChangeListener(workerDialog);
        workerDialog.setVisible(true);
    }

    public void setJob(Job job) {
        this.job = job;
        this.job.addPropertyChangeListener(workerDialog);
    }
    
    @Override
    protected T doInBackground() throws Exception {
        this.job.run();
        return null;
    }
   
}