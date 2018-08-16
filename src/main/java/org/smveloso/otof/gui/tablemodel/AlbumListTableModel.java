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
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;

public class AlbumListTableModel extends AbstractTableModel implements PropertyChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(AlbumListTableModel.class);
    
    private MainFrameState mainFrameState;
    
    // id, name, serverside, mountpoint(!!!), nro of photos (ira variar cf tipo de album (!!!))
    //TODO como lidar com tipos diferentes de album ?
    private static final Integer COLUMNS = 5;

    //TODO localization someday ?
    
    private static final String COL_NAME_ID = "ID";
    private static final String COL_NAME_NAME = "nome";
    private static final String COL_NAME_SERVER_SIDE = "remoto ?";
    private static final String COL_NAME_MOUNT_POINT = "mount point";
    private static final String COL_NAME_NRO_FOTOS = "fotos";
    
    private static final String[] COLUMN_NAMES = new String[] {COL_NAME_ID,
                                                               COL_NAME_NAME,
                                                               COL_NAME_SERVER_SIDE,
                                                               COL_NAME_MOUNT_POINT,
                                                               COL_NAME_NRO_FOTOS};
    
    private static final Class[] COLUMN_CLASSES = new Class[] {Integer.class,
                                                               String.class,
                                                               Boolean.class,
                                                               String.class,
                                                               Integer.class};

    private List<Album> getAlbums() {
        if (null == mainFrameState) {
            return new ArrayList<>();
        } else {
            return mainFrameState.getAlbumList();
        }
    }
    
    @Override
    public int getRowCount() {
        return getAlbums().size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Album album = getAlbums().get(rowIndex);
        Object result = null;
        switch (columnIndex) {
            case 0: result = album.getId(); break;
            case 1: result = album.getName(); break;
            case 2: result = album.isServerSide(); break;
            case 3: result = ((LocalFileSystemAlbum) album).getMountPointAsString(); break;
            case 4: result = -1; break;
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
        if (evt.getPropertyName().equals(MainFrameProperties.SET_ALBUM_LIST.name())) {
            logger.trace("firing table data changed");
            fireTableDataChanged();
        }
    }
    
}
