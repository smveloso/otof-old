package org.smveloso.otof.gui.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;

public class AlbumListTableModel extends AbstractTableModel {

    private static final Logger logger = LoggerFactory.getLogger(AlbumListTableModel.class);
    
    private List<Album> albums = new ArrayList<>();
    
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
    
    @Override
    public int getRowCount() {
        return albums.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Album album = albums.get(rowIndex);
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
    
    public void setAlbums(List<Album> albums) {
        if (albums == null) {
            throw new IllegalArgumentException("null album!");
        }
        this.albums = albums;
    }
    
}
