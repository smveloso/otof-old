package org.smveloso.otof.gui.job;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** 
 *
 * @author sergiomv
 */
public abstract class Job implements Runnable {
    
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    private String mensagem = "";
    private Integer progresso = 0;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        String oldMensagem = this.mensagem;
        this.mensagem = mensagem;
        this.pcs.firePropertyChange("mensagem", oldMensagem, mensagem);
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        Integer oldProgresso = this.progresso;
        this.progresso = progresso;
        this.pcs.firePropertyChange("progress", oldProgresso, progresso);
    }

}
