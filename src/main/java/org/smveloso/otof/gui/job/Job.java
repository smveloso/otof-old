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

}
