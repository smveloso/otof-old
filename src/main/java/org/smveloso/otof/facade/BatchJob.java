package org.smveloso.otof.facade;

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
