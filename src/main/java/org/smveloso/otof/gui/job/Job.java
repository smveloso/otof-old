package org.smveloso.otof.gui.job;

import org.smveloso.otof.facade.FacadeException;
import org.smveloso.otof.gui.job.WorkerDelegate;

/**
 *
 * @author sergiomv
 */
public interface Job {
    
    public void execute() throws FacadeException;
    
}
