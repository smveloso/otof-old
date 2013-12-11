package org.smveloso.otof.facade;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.smveloso.otof.em.EmException;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.em.JpaManager;
import org.smveloso.otof.exinf.ExinfFacade;
import org.smveloso.otof.model.Foto;

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

    
    public void executaVarredura(File baseDir) throws FacadeException {
        
        try {
        
            //TODO adaptar para rodar com um 'progress bar' visual

            // sanidade
            if ((null == baseDir) || (!baseDir.exists()) || (!baseDir.isDirectory()) || (!baseDir.canRead())) {
                throw new FacadeException("Varredura não pode começar. Erro no acesso ao diretório base.");
            }

            Collection<File> allfiles = FileUtils.listFiles(baseDir, new String[] {"jpg"}, true);

            for (File file:allfiles) {

                //LOG
                Foto jaExiste = fotoJpaController.findFotoByArquivo(file.getAbsolutePath());
                if (null != jaExiste) {
                    // LOG
                    continue;
                }

                // computar digest e cadastrar

                Foto naoExiste = new Foto();
                naoExiste.setArquivo(file.getAbsolutePath());
                naoExiste.setDigest("todo");
                naoExiste.setDataTirada(ExinfFacade.getDataTirada(file));
                naoExiste.setTamanhoArquivo(file.length());

                fotoJpaController.create(naoExiste);

            }
        
        } catch (EmException e) {
            //LOG
            //TODO roll back ???
            throw new FacadeException("Erro durante varredura:" + e.getMessage(), e);
        }

    }
    
}
