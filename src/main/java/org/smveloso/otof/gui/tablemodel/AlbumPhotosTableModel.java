package org.smveloso.otof.gui.tablemodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.gui.MainFrameProperties;
import org.smveloso.otof.gui.MainFrameState;
import org.smveloso.otof.model.Photo;

public class AlbumPhotosTableModel extends AbstractTableModel implements PropertyChangeListener {

   private static final Logger logger = LoggerFactory.getLogger(AlbumListTableModel.class);
    
    private MainFrameState mainFrameState;
    
    // id, name, serverside, mountpoint(!!!), nro of photos (ira variar cf tipo de album (!!!))
    //TODO como lidar com tipos diferentes de album ?
    private static final Integer COLUMNS = 5;

    //TODO localization someday ?
    
    private static final String COL_NAME_ID = "ID";
    private static final String COL_NAME_DATE_TAKEN = "data";
    private static final String COL_NAME_FILE_NAME = "file name";
    private static final String COL_NAME_FILE_SIZE = "file size";
    private static final String COL_NAME_FILE_DIGEST = "file digest";
    
    private static final String[] COLUMN_NAMES = new String[] {COL_NAME_ID,
                                                               COL_NAME_DATE_TAKEN,
                                                               COL_NAME_FILE_NAME,
                                                               COL_NAME_FILE_SIZE,
                                                               COL_NAME_FILE_DIGEST};
    
    private static final Class[] COLUMN_CLASSES = new Class[] {Integer.class,
                                                               String.class,
                                                               String.class,
                                                               Long.class,
                                                               String.class};

    private List<Photo> getAlbumPhotos() {
        if (null == mainFrameState) {
            return new ArrayList<>();
        } else {
            return mainFrameState.getAlbumPhotosList();
        }
    }
    
    @Override
    public int getRowCount() {
        return getAlbumPhotos().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Photo photo = getAlbumPhotos().get(rowIndex);
        Object result = null;
        switch (columnIndex) {
            case 0: result = photo.getId(); break;
            case 1: result = photo.getDateTaken(); break;
            case 2: result = "todo"; break;   // file name is recorded in location, not in photo. 
                                              // there maybe more than one file per photo per album
            case 3: result = photo.getFileSize(); break;
            case 4: result = photo.getFileDigest(); break;
        }
        return result;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        logger.warn(">>> setValueAt(...): " + rowIndex + "," + columnIndex);
        logger.warn("VALUE:" + aValue);
        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //TODO revisar !!!
        return true;
    }
    
    public void associateToState(MainFrameState mainFrameState) {
        this.mainFrameState = mainFrameState;
        this.mainFrameState.addPropertyChangeListener(this);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        logger.trace(">>> propertyChange: "+ evt.getPropertyName());
        //if (evt.getPropertyName().equals(MainFrameProperties.SET_CURRENT_ALBUM.name())) {
        if (evt.getPropertyName().equals(MainFrameProperties.SET_CURRENT_ALBUM_PHOTO_LIST.name())) {
            logger.trace("firing table data changed");
            fireTableDataChanged();
        }
    }    
}
