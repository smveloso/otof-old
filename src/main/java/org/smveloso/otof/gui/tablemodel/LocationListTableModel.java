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
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;

public class LocationListTableModel extends AbstractTableModel implements PropertyChangeListener {

   private static final Logger logger = LoggerFactory.getLogger(LocationListTableModel.class);
    
    private MainFrameState mainFrameState;
    
    // id, name, serverside, mountpoint(!!!), nro of photos (ira variar cf tipo de album (!!!))
    //TODO como lidar com tipos diferentes de album ?
    private static final Integer COLUMNS = 6;

    //TODO localization someday ?
    
    private static final String COL_NAME_ID = "ID";
    private static final String COL_NAME_DATE_TAKEN = "data";
    private static final String COL_NAME_FILE_NAME = "file name";
    private static final String COL_NAME_FILE_SIZE = "file size";
    private static final String COL_NAME_FILE_DIGEST = "file digest";
    private static final String COL_NAME_ALBUM = "album";
    
    
    private static final String[] COLUMN_NAMES = new String[] {COL_NAME_ID,
                                                               COL_NAME_DATE_TAKEN,
                                                               COL_NAME_FILE_NAME,
                                                               COL_NAME_FILE_SIZE,
                                                               COL_NAME_FILE_DIGEST,
                                                               COL_NAME_ALBUM};
    
    private static final Class[] COLUMN_CLASSES = new Class[] {Integer.class,
                                                               String.class,
                                                               String.class,
                                                               Long.class,
                                                               String.class,
                                                               String.class};

    private List<Location> getAllLocations() {
        if (null == mainFrameState) {
            return new ArrayList<>();
        } else {
            return mainFrameState.getLocationsList();
        }
    }
    
    @Override
    public int getRowCount() {
        return getAllLocations().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Location location = getAllLocations().get(rowIndex);
        Object result = null;
        switch (columnIndex) {
            case 0: result = location.getId(); break;
            case 1: result = location.getPhoto().getDateTaken(); break;
            case 2: result = location.getPath(); break;
            case 3: result = location.getPhoto().getFileSize(); break;
            case 4: result = location.getPhoto().getFileDigest(); break;
            case 5: result = location.getAlbum().getName();
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
        super.setValueAt(aValue, rowIndex, columnIndex);
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
        if (evt.getPropertyName().equals(MainFrameProperties.SET_LOCATION_LIST.name())) {
            logger.trace("firing table data changed");
            fireTableDataChanged();
        }
    }    
}
