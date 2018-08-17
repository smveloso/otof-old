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
import org.smveloso.otof.model.Album;

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

    private Album currentAlbum;
    
    public List<Album> getAlbumList() {
        return albumList;
    }
    
    public void setAlbumList(List<Album> newAlbumList) {
        List<Album> old = this.albumList;
        this.albumList = newAlbumList;
        pcs.firePropertyChange(MainFrameProperties.SET_ALBUM_LIST.name(), old, this.albumList);
    }

    ListSelectionListener albumListSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            logger.trace(">>> albumListSelectionListener.valueChanged(...)");
            logger.trace("Adjusting ?" + e.getValueIsAdjusting());
            logger.trace("First     : " + e.getFirstIndex());
            logger.trace("Last      : " + e.getLastIndex());                 
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            logger.trace("CLASS IS: " + lsm.getClass().toString());
            if (lsm instanceof Component) {
                String tableName = ((Component) lsm).getName();
                logger.trace("Component: " + tableName);
            }
        }            
    };

}
