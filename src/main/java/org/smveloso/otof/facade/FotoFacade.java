package org.smveloso.otof.facade;

import java.io.File;
import java.util.concurrent.ExecutionException;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.em.JpaManager;
import org.smveloso.otof.gui.WaitWindowWorkerDialog;

/**
 *
 * @author sergiomv
 */
public class FotoFacade {

    private FotoJpaController fotoJpaController;

    private FotoFacade() {
        fotoJpaController = new FotoJpaController(JpaManager.getInstance().getFactory());
    }
  
    private static FotoFacade instance = null;
    
    public static synchronized FotoFacade getInstance() {
        if (null == instance) {
            instance = new FotoFacade();
        }
        return instance;
    }

    public synchronized void executaVarredura(File baseDir) throws FacadeException {
        
        try {
        
            //TODO adaptar para rodar com um 'progress bar' visual

            // sanidade
            if ((null == baseDir) || (!baseDir.exists()) || (!baseDir.isDirectory()) || (!baseDir.canRead())) {
                throw new FacadeException("Varredura não pode começar. Erro no acesso ao diretório base.");
            }

            Varredor varredor = new Varredor(baseDir);
            varredor.setFotoJpaController(fotoJpaController);
            
            VarredorInitializeJob initJob = new VarredorInitializeJob();
            initJob.setVarredor(varredor);
            
            JobSwingWorker<Void,Void> jobWorker = new JobSwingWorker<>();
            jobWorker.setJob(initJob);
            WaitWindowWorkerDialog workerDialogForInit = new WaitWindowWorkerDialog(null, jobWorker, false, true);  // displays messages but no progress
            workerDialogForInit.setVisible(true);
            
            jobWorker.get(); // waits ...
            
            VarredorBatchJob batchJob = new VarredorBatchJob();
            batchJob.setVarredor(varredor);
            
            BatchJobSwingWorker<Void,Void> batchJobWorker = new BatchJobSwingWorker<>();
            batchJobWorker.setBatchJob(batchJob);
            
            WaitWindowWorkerDialog workerDialog = new WaitWindowWorkerDialog(null, batchJobWorker);
            workerDialog.setVisible(true);
            
            batchJobWorker.get(); // waits ...
            
             
        /*
        } catch (EmException | DigestException | ExinfException e) {
            //LOG
            //TODO roll back ???
            throw new FacadeException("Erro durante varredura:" + e.getMessage(), e);
        */  
            
        } catch (InterruptedException| ExecutionException e) {
            throw new FacadeException("Job interrupted or failed: " + e.getMessage());
        } finally {
            
        }

    }
    
    public synchronized void organiza(boolean everything,
                                       boolean ignoreArchived,
                                       boolean createCopies,
                                       boolean createManifest,
                                       File destDir,
                                       File manifestFile,
                                       long bytesPerUnit) throws FacadeException
    {
    
        try {
        
            // validation ?!?

            Organizador organizador = new Organizador();
            organizador.setProcessEverything(everything);
            organizador.setIgnoreArchived(ignoreArchived);
            organizador.setCreateCopies(createCopies);
            organizador.setCreateManifest(createManifest);
            organizador.setDestDir(destDir);
            organizador.setManifest(manifestFile);
            organizador.setBytesPerUnit(bytesPerUnit);

            organizador.setFotoJpaController(fotoJpaController);

            OrganizadorJob job = new OrganizadorJob();
            job.setOrganizador(organizador);

            JobSwingWorker<Void,Void> jobWorker = new JobSwingWorker<>();
            jobWorker.setJob(job);
            WaitWindowWorkerDialog workerDialogForInit = new WaitWindowWorkerDialog(null, jobWorker, false, true);  // displays messages but no progress
            workerDialogForInit.setVisible(true);

            jobWorker.get(); // waits ...

        } catch (InterruptedException| ExecutionException e) {
            throw new FacadeException("Job interrupted or failed: " + e.getMessage());
        } finally {
            
        }        
        
    }

    public synchronized void limpa(Limpador.OPERACAO op) throws FacadeException {

        try {
        
            Limpador limpador = new Limpador();
            limpador.setFotoJpaController(fotoJpaController);
            limpador.setOperacao(op);
            LimpadorJob job = new LimpadorJob();
            job.setLimpador(limpador);

            JobSwingWorker<Void, Void> jobWorker = new JobSwingWorker<>();
            jobWorker.setJob(job);
            WaitWindowWorkerDialog workerDialogForInit = new WaitWindowWorkerDialog(null, jobWorker, false, true);  // displays messages but no progress
            workerDialogForInit.setVisible(true);
            
            jobWorker.get(); // waits ...
        
        } catch (InterruptedException| ExecutionException e) {
            throw new FacadeException("Job interrupted or failed: " + e.getMessage());
        } finally {
            
        }        
        
        
    }
    
}
