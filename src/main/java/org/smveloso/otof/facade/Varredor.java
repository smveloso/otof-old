package org.smveloso.otof.facade;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.io.FileUtils;
import org.smveloso.otof.digest.DigestException;
import org.smveloso.otof.digest.DigestFacade;
import org.smveloso.otof.em.EmException;
import org.smveloso.otof.em.FotoJpaController;
import org.smveloso.otof.exinf.ExinfException;
import org.smveloso.otof.exinf.ExinfFacade;
import org.smveloso.otof.model.Foto;

/**
 *
 * @author sergiomv
 */
public class Varredor {

    private FotoJpaController fotoJpaController;
    
    private File baseDir;
    private File currentFile;
    private Collection<File> files;
    private Iterator<File> iterator;
    private int remainingFiles;
    
    public Varredor(File baseDir) {
        this.baseDir = baseDir;
    }

    public void initialize() throws FacadeException {

        if ((null == baseDir) || (!baseDir.exists()) || (!baseDir.isDirectory()) || (!baseDir.canRead())) {
            throw new FacadeException("Varredura não pode começar. Erro no acesso ao diretório base.");
        }

        this.files = FileUtils.listFiles(baseDir, new String[]{"jpg", "JPG"}, true);
        this.iterator = this.files.iterator();
        this.currentFile = null;
        this.remainingFiles = this.files.size();
        
    }

    public void setFotoJpaController(FotoJpaController fotoJpaController) {
        this.fotoJpaController = fotoJpaController;
    }
    
    public File getCurrentFile() {
        return this.currentFile;
    }
    
    public int getNumberOfFiles() {
        return files.size();
    }

    public int getRemainingFiles() {
        return remainingFiles;
    }

    public boolean hasMoreFiles() {
        return this.iterator.hasNext();
    }
    
    public void processNextFile() throws FacadeException {

        try {
        
            if (this.iterator.hasNext()) {
                this.currentFile = this.iterator.next();

                //LOG
                Foto jaExiste = fotoJpaController.findFotoByArquivo(this.currentFile.getAbsolutePath());
                if (null == jaExiste) {

                    // computar digest e cadastrar
                    Foto naoExiste = new Foto();
                    naoExiste.setArquivo(this.currentFile.getAbsolutePath());
                    naoExiste.setDigest(DigestFacade.getSha1HexEncoded(this.currentFile));
                    naoExiste.setDataTirada(ExinfFacade.getDataTirada(this.currentFile));
                    naoExiste.setTamanhoArquivo(this.currentFile.length());

                    fotoJpaController.create(naoExiste);

                }
                
            } else {
                throw new FacadeException("Illegal state: nothing to process.");
            }
        
        } catch (DigestException| EmException| ExinfException e) {
            throw new FacadeException(e);
        }
        
    }
    
    
}
