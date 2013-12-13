package org.smveloso.otof.facade;

/**
 *
 * @author sergiomv
 */
public class DefaultWorkerDelegate implements WorkerDelegate {

    private MensagemSwingWorker worker;
    
    @Override
    public void setWorker(MensagemSwingWorker worker) {
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
