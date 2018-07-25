package org.smveloso.otof.service;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.smveloso.otof.model.LocalFileSystemAlbum;

/**
 *
 * @author sergio
 */
public class LocalFileSystemAlbumUpdater extends AlbumUpdater {

    private final File baseDirectory;
    
    private Iterator<File> fileIterator;
    private Collection<File> files;
    
    public LocalFileSystemAlbumUpdater(LocalFileSystemAlbum album) {
        super(album);
        baseDirectory = getFileSystemAlbum().getMountPoint();
    }
    
    private LocalFileSystemAlbum getFileSystemAlbum() {
        return (LocalFileSystemAlbum) this.album;
    }
    
    @Override
    protected void actualInitialization() {
        // TODO: criterios de aceitacao de arquivos ainda hard-coded         
        // TODO: escolher ou implementar lib para busca recurssiva de arquivos
        files = FileUtils.listFiles(baseDirectory, new String[]{"jpg", "JPG"}, true);
        fileIterator = files.iterator();
    }

    @Override
    public int getNumberOfFilesToProcess() {
        return files.size();
    }

    @Override
    public Iterator<File> getFileIterator() {
        return fileIterator;
    }    
    
}
