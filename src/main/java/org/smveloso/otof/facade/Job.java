package org.smveloso.otof.facade;

/**
 *
 * @author sergiomv
 */
public interface Job {
    
    public void execute() throws FacadeException;
    
    public void setWorkerDelegate(WorkerDelegate workerDelegate);    
    
}
