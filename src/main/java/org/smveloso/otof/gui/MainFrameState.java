package org.smveloso.otof.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Photo;

public class MainFrameState  {

    private static final Logger logger = LoggerFactory.getLogger(MainFrameState.class);
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
   
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    // backend do albumlistablemodel
    private List<Album> albumList = new ArrayList<>();

    // backend do albumphotostablemodel
    private List<Photo> albumPhotosList = new ArrayList<>();
    
    private Album album;

    private Integer currentPageInAlbumPhotos = 1;

    public Integer getCurrentPageInAlbumPhotos() {
        return currentPageInAlbumPhotos;
    }

    public void setCurrentPageInAlbumPhotos(Integer currentPageInAlbumPhotos) {
        throw new UnsupportedOperationException("not ready");
        //this.currentPageInAlbumPhotos = currentPageInAlbumPhotos;
    }
    
    public List<Album> getAlbumList() {
        return albumList;
    }
    
    public void setAlbumList(List<Album> newAlbumList) {
        List<Album> old = this.albumList;
        this.albumList = newAlbumList;
        pcs.firePropertyChange(MainFrameProperties.SET_ALBUM_LIST.name(), old, this.albumList);
    }

    public List<Photo> getAlbumPhotosList() {
        return albumPhotosList;
    }
    
    //TODO diferenciar por tab: album de qual tab ??
    public Album getAlbum() {
        return album;
    }

    //TODO diferenciar por tab: album de qual tab ??
    public boolean isAlbumSelected() {
        return (getAlbum() != null);
    }
    
    public void clearCurrentAlbum() {
        setAlbum(null);
    }
    
    public void setAlbum(Album album) {
        logger.debug(">>> setAlbum()");
        logger.trace("ALBUM: " + ((album != null)?album.toString():"null"));
        Album old = this.album;
        this.album = album;
        pcs.firePropertyChange(MainFrameProperties.SET_CURRENT_ALBUM.name(), old, this.album);
        logger.debug("<<< setAlbum()");
    }
    
    public void setAlbumPhotosList(List<Photo> albumPhotosList) {
        logger.debug(">>> setAlbumPhotosList()");
        List<Photo> old = this.albumPhotosList;
        this.albumPhotosList = albumPhotosList;
        pcs.firePropertyChange(MainFrameProperties.SET_CURRENT_ALBUM_PHOTO_LIST.name(), old, this.albumPhotosList);
        logger.debug("<<< setAlbumPhotosList()");
    }
    
}
