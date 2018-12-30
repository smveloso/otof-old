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

    private List<Album> albumList = new ArrayList<>();

    private List<Photo> photosList = new ArrayList<>();
    
    private Album album;

    private Photo photo;
    
    public List<Album> getAlbumList() {
        return albumList;
    }
    
    public void setAlbumList(List<Album> newAlbumList) {
        List<Album> old = this.albumList;
        this.albumList = newAlbumList;
        pcs.firePropertyChange(MainFrameProperties.SET_ALBUM_LIST.name(), old, this.albumList);
    }

    public List<Photo> getPhotosList() {
        return photosList;
    }
    
    public Album getAlbum() {
        return album;
    }

    public Photo getPhoto() {
        return photo;
    }
    
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

    public void setPhoto(Photo photo) {
        logger.debug(">>> setPhoto()");
        logger.trace("PHOTO: " + ((photo != null)?photo.toString():"null"));
        Photo old = this.photo;
        this.photo = photo;
        pcs.firePropertyChange(MainFrameProperties.SET_CURRENT_PHOTO.name(), old, this.photo);
        logger.debug("<<< setPhoto()");
    }

    /** <p/> Define o conteúdo da lista de Photos.
     *  <p/> Dispara MainFrameProperties.SET_PHOTO_LIST
     * 
     * @param photosList 
     */
    public void setPhotosList(List<Photo> photosList) {
        logger.debug(">>> setPhotosList()");
        List<Photo> old = this.photosList;
        this.photosList = photosList;
        pcs.firePropertyChange(MainFrameProperties.SET_PHOTO_LIST.name(), old, this.photosList);
        logger.debug("<<< setPhotosList()");
    }
   
}