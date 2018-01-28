package org.smveloso.otof.gui.job;

/**
 *
 * @author sergiomv
 */
public class DefaultWorkerDelegate implements WorkerDelegate {

    private DisplayMessageSwingWorker worker;
    
    @Override
    public void setWorker(DisplayMessageSwingWorker worker) {
        this.worker = worker;
    }

    @Override
    public void setMensagem(String mensagem) {
        if (null != this.worker) {
            worker.setMensagem(mensagem);
        }
    }

    @Override
    public void setProgress(int progress) {
        if (null != this.worker) {
            worker.setProgressFromInside(progress);
        }
    }

}
