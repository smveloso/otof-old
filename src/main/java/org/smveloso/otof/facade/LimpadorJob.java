package org.smveloso.otof.facade;

/**
 *
 * @author sergio
 */
public class LimpadorJob implements Job {

    private Limpador limpador = null;

    public void setLimpador(Limpador limpador) {
        this.limpador = limpador;
    }

    @Override
    public void execute() throws FacadeException {
        limpador.eliminarDuplicadasPorDigest();
    }

    @Override
    public void setWorkerDelegate(WorkerDelegate workerDelegate) {
        limpador.setWorkerDelegate(workerDelegate);
    }
    
}
