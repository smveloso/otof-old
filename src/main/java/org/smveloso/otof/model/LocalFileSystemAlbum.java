package org.smveloso.otof.model;

import java.io.File;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class LocalFileSystemAlbum  extends Album {
    
    public LocalFileSystemAlbum() {
        serverSide = false;
    }

    /** A File object that points to the
     *  base directory of this album.
     */     
    @Transient
    private File mountPoint;

    @Column(nullable = false)
    private String mountPointAsString;
    
    public File getMountPoint() {
        // TODO quando objeto eh lido do banco, o 
        //      setter de mountPointAsString nao
        //      eh chamado. Ha como fazer ?
        if ((this.mountPoint) == null && (this.mountPointAsString != null)) {
            this.mountPoint = new File(this.mountPointAsString);
        }
        return mountPoint;
    }

    public void setMountPoint(File mountPoint) {
        this.mountPoint = mountPoint;
        this.mountPointAsString = mountPoint.getAbsolutePath();
    }

    public String getMountPointAsString() {
        return mountPointAsString;
    }

    public void setMountPointAsString(String mountPointAsString) {
        this.mountPointAsString = mountPointAsString;
        this.mountPoint = new File(this.mountPointAsString);
    }
    
}
