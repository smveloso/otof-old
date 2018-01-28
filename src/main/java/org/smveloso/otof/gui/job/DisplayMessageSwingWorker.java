package org.smveloso.otof.gui.job;

import javax.swing.SwingWorker;

/**
 *
 * @author sergiomv
 */
public abstract class DisplayMessageSwingWorker<T,V> extends SwingWorker<T, V> {
    
    protected String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        String old = this.mensagem;
        this.mensagem = mensagem;
        firePropertyChange("mensagem", old, mensagem);
    }    
    
    public void setProgressFromInside(int progress) {
        setProgress(progress);
    }

}
