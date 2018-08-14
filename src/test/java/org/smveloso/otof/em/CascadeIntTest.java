package org.smveloso.otof.em;

import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;
import org.smveloso.otof.test.JpaBaseTest;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author sergio
 */
public class CascadeIntTest extends JpaBaseTest {
       
    private static final Logger logger = LoggerFactory.getLogger(CascadeIntTest.class);
    
    public CascadeIntTest() {
    }

    @Test(groups="jpa-test")
    public void deleteLocationSingleLocation() throws Exception {
        logger.debug(">>> deleteLocationSingleLocation");

        long LOCATION_ID = 1000l;
        long ALBUM_ID = 1000l;
        long PHOTO_ID = 1000l;
        
        Location location = LocationDAO.getInstance().findLocation(LOCATION_ID);
        assertNotNull(location,"cant test: null location");
        assertNotNull(location.getAlbum(),"cant test: null album");
        assertNotNull(location.getPhoto(),"cant test: null photo");
        assertEquals(location.getAlbum().getId().longValue(),ALBUM_ID,"cant test: wrong album id");
        assertEquals(location.getPhoto().getId().longValue(),PHOTO_ID,"cant test: wrong photo id");
        
        LocationDAO.getInstance().destroy(LOCATION_ID);
        
        location = LocationDAO.getInstance().findLocation(LOCATION_ID);
        assertNull(location,"location nao foi removida do banco");
        
        Album album = AlbumDAO.getInstance().findAlbum(ALBUM_ID);
        Photo photo = PhotoDAO.getInstance().findFoto(PHOTO_ID);
        
        assertNotNull(album,"album foi removido!");
        assertNotNull(photo,"photo foi removida!");
        
    }

    @Test(groups = "jpa-test")
    public void deleteAlbum() throws Exception {
        logger.debug(">>> deleteAlbum");

        long LOCATION_ID = 2000l;
        long LOCATION_ID_2 = 2001l;
        long ALBUM_ID = 2000l;
        long PHOTO_ID = 2000l;

        assertNotNull(LocationDAO.getInstance().findLocation(LOCATION_ID),"cant test: location not found");
        assertNotNull(LocationDAO.getInstance().findLocation(LOCATION_ID_2),"cant test: location 2 not found");
        assertNotNull(PhotoDAO.getInstance().findFoto(PHOTO_ID),"cant test: photo not found");
        assertNotNull(AlbumDAO.getInstance().findAlbum(ALBUM_ID),"cant test: album not found");
        
        AlbumDAO.getInstance().destroy(ALBUM_ID);
        
        assertNull(LocationDAO.getInstance().findLocation(LOCATION_ID),"location not deleted !");
        assertNull(LocationDAO.getInstance().findLocation(LOCATION_ID_2),"location 2 not deleted !");
        assertNotNull(PhotoDAO.getInstance().findFoto(PHOTO_ID),"photo deleted !");
        assertNull(AlbumDAO.getInstance().findAlbum(ALBUM_ID),"album not deleted !");
        
    }
    
    @Test(groups = "jpa-test")
    public void deletePhoto() throws Exception {
        logger.debug(">>> deletePhoto");

        long LOCATION_ID = 2000l;
        long LOCATION_ID_2 = 2001l;
        long ALBUM_ID = 2000l;
        long PHOTO_ID = 2000l;

        assertNotNull(LocationDAO.getInstance().findLocation(LOCATION_ID),"cant test: location not found");
        assertNotNull(LocationDAO.getInstance().findLocation(LOCATION_ID_2),"cant test: location 2 not found");
        assertNotNull(PhotoDAO.getInstance().findFoto(PHOTO_ID),"cant test: photo not found");
        assertNotNull(AlbumDAO.getInstance().findAlbum(ALBUM_ID),"cant test: album not found");
        
        PhotoDAO.getInstance().destroy(PHOTO_ID);
        
        assertNull(LocationDAO.getInstance().findLocation(LOCATION_ID),"location not deleted !");
        assertNull(LocationDAO.getInstance().findLocation(LOCATION_ID_2),"location 2 not deleted !");
        assertNull(PhotoDAO.getInstance().findFoto(PHOTO_ID),"photo not deleted !");
        assertNotNull(AlbumDAO.getInstance().findAlbum(ALBUM_ID),"album deleted !");
        
    }

    @Override
    protected void prepareSettings() {
        logger.debug(">>> prepareSettings");
        dataSetLocation = "org/smveloso/otof/em/cascadeTestDS.xml";
        beforeTestOperations.add(DatabaseOperation.DELETE_ALL);
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
        afterTestOperations.add(DatabaseOperation.DELETE_ALL);
    }
    
}
