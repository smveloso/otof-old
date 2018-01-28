package org.smveloso.otof.gui.job;

/**
 *
 * @author sergiomv
 */
public class BatchJobSwingWorker<T,V> extends DisplayMessageSwingWorker<T, V> {

    private BatchJob batchJob;
    
    public BatchJob getBatchJob() {
        return batchJob;
    }

    public void setBatchJob(BatchJob batchJob) {
        this.batchJob = batchJob;
    }
    
    @Override
    protected T doInBackground() throws Exception {

        setProgress(0);
        
        while (batchJob.hasMoreItems()) {
            Object item = batchJob.getLastProcessedItem();
            batchJob.process(item);
            setMensagem(batchJob.getMessage());
            setProgress(batchJob.getProgress());
        }
    
        setProgress(100);
        
        return null; //TODO improve
        
    }
    
   

}
