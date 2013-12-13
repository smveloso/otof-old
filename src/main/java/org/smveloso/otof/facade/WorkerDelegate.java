package org.smveloso.otof.facade;

/**
 *
 * @author sergiomv
 */
public interface WorkerDelegate {

    public void setWorker(MensagemSwingWorker worker);

    public void setMensagem(String mensagem);

    public void setProgress(int progress);

}
