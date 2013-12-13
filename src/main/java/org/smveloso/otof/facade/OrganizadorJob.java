package org.smveloso.otof.facade;

/**
 *
 * @author sergiomv
 */
public class OrganizadorJob implements Job {

    private Organizador organizador = null;

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    @Override
    public void setWorkerDelegate(WorkerDelegate workerDelegate) {
        this.organizador.setWorkerDelegate(workerDelegate);
    }

    @Override
    public void execute() throws FacadeException {
        this.organizador.organize();
    }
    
}
