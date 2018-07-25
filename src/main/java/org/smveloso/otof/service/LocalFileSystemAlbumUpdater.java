package org.smveloso.otof.service;

import java.io.File;
import java.util.Iterator;
import org.smveloso.otof.model.LocalFileSystemAlbum;

/**
 *
 * @author sergio
 */
public class LocalFileSystemAlbumUpdater extends AlbumUpdater {

    public LocalFileSystemAlbumUpdater(LocalFileSystemAlbum album) {
        super(album);
    }
    
    @Override
    protected void actualInitialization() {
        
    }

    @Override
    public int getNumberOfFilesToProcess() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<File> getFileIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
}
