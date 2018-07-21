package org.smveloso.otof.model;

import java.io.File;

/**
 *
 * @author sergio
 */
public class LocalFileSystemAlbum  extends Album {

    //TODO entity stuff !?
    
    public LocalFileSystemAlbum() {
        setServerSide(false);
    }

    /** A File object that points to the
     *  base directory of this album.
     */     
    private File mountPoint;

    public File getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(File mountPoint) {
        this.mountPoint = mountPoint;
    }

}
