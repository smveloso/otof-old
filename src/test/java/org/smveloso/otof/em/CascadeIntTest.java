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
        logger.debug(">>> deleteLocation");

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

    //TODO remocao do album deve remover locations mas nao as photos !!!
    
    //TODO remocao de photo deve remover locations nos diversos
    //     albums, mas nao os proprios albums !!!

    @Override
    protected void prepareSettings() {
        logger.debug(">>> prepareSettings");
        dataSetLocation = "org/smveloso/otof/em/cascadeTestDS.xml";
        beforeTestOperations.add(DatabaseOperation.DELETE_ALL);
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
        afterTestOperations.add(DatabaseOperation.DELETE_ALL);
    }
    
}
