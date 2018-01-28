package org.smveloso.otof.gui.job;

/**
 *
 * @author sergiomv
 */
public interface WorkerDelegate {

    public void setWorker(DisplayMessageSwingWorker worker);

    public void setMensagem(String mensagem);

    public void setProgress(int progress);

}
