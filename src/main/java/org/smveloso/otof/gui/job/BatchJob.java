package org.smveloso.otof.gui.job;

import org.smveloso.otof.facade.FacadeException;

/**
 *
 * @author sergiomv
 */
public interface BatchJob<T> {

    public boolean hasMoreItems();
    
    public T getLastProcessedItem();
    
    public int getProgress();
    
    public String getMessage();
    
    public void process(T item) throws FacadeException;
        
}
