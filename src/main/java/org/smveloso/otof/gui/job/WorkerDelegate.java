package org.smveloso.otof.gui.job;

import org.smveloso.otof.gui.swingworker.DisplayMessageSwingWorker;

/**
 *
 * @author sergiomv
 */
public interface WorkerDelegate {

    public void setWorker(DisplayMessageSwingWorker worker);

    public void setMensagem(String mensagem);

    public void setProgress(int progress);

}
