package org.smveloso.otof.facade;

import javax.swing.SwingWorker;

/**
 *
 * @author sergiomv
 */
public class BatchJobSwingWorker<T,V> extends SwingWorker<T, V> {

    private BatchJob batchJob;

    private String mensagem;
    
    public BatchJob getBatchJob() {
        return batchJob;
    }

    public void setBatchJob(BatchJob batchJob) {
        this.batchJob = batchJob;
    }
    
    public void setMensagem(String mensagem) {
        String old = this.mensagem;
        this.mensagem = mensagem;
        firePropertyChange("mensagem", old, mensagem);
    }    
    
    @Override
    protected T doInBackground() throws Exception {

        setProgress(0);
        
        while (batchJob.hasMoreItems()) {
            Object item = batchJob.getNextItem();
            batchJob.process(item);
            setMensagem(batchJob.getMessage());
            setProgress(batchJob.getProgress());
        }
    
        setProgress(100);
        
        return null; //TODO improve
        
    }
    
   

}
