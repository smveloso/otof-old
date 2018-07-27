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
        return mountPoint;
    }

    public void setMountPoint(File mountPoint) {
        this.mountPoint = mountPoint;
    }

    public String getMountPointAsString() {
        return mountPointAsString;
    }

    public void setMountPointAsString(String mountPointAsString) {
        this.mountPointAsString = mountPointAsString;
    }
    
}
