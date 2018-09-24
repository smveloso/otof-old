package org.smveloso.otof.gui;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.em.AlbumDAO;
import org.smveloso.otof.em.LocationDAO;
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
    
    private Album currentAlbum;

    private Integer currentPageInAlbumPhotos = 1;
    
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
    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    //TODO diferenciar por tab: album de qual tab ??
    public boolean isAlbumSelected() {
        return (getCurrentAlbum() != null);
    }
    
    public void clearCurrentAlbumIndex() {
        setCurrentAlbumIndex(-1);
    }
    
    public void setCurrentAlbumIndex(int index) {
        logger.debug(">>> setCurrentAlbumIndex() : " + index);
        if (index == -1) {
            setCurrentAlbum(null);
        } else {
            setCurrentAlbum(getAlbumList().get(index));
        }
        logger.debug("<<< setCurrentAlbumIndex()");
    }
    
    private void setCurrentAlbum(Album currentAlbum) {
        logger.debug(">>> setCurrentAlbum()");
        logger.trace("ALBUM: " + ((currentAlbum != null)?currentAlbum.getName():"null"));
        Album old = this.currentAlbum;
        this.currentAlbum = currentAlbum;
        if (currentAlbum != null) {
            this.albumPhotosList = LocationDAO.getInstance().getAlbumPhotos(currentAlbum, currentPageInAlbumPhotos, 10);
        } else {
            this.albumPhotosList = new ArrayList<>();
        }
        pcs.firePropertyChange(MainFrameProperties.SET_CURRENT_ALBUM.name(), old, this.currentAlbum);
    }

    
    
    
}
