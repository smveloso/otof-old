package org.smveloso.otof.em;

import org.dbunit.operation.DatabaseOperation;
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
public class LocationDAOTest extends JpaBaseTest {
    
    public LocationDAOTest() {
    }

    @Test(groups="jpa-test")
    public void testFindLocation() throws Exception {
        Location location = LocationDAO.getInstance().findLocation(1000l);
        assertNotNull(location,"location not found (by id)");
        Album album = location.getAlbum();
        Photo photo = location.getPhoto();
        
        assertNotNull(album,"album null in location");
        assertNotNull(photo,"photo null in location");
        
        assertNotNull(album.getName(),"null album name");
        assertEquals(album.getName(),"ALBUM ONE","wrong album name");
        
        assertNotNull(photo.getFileDigest(),"null digest for photo file");
        assertEquals(photo.getFileDigest(),"12345678901234567890","wrong photo file digest");

    }

    @Test(groups="jpa-test")
    public void testFindLocationByAlbumAndPath() throws Exception {
        Album album = new LocalFileSystemAlbum(); album.setId(1000l);
        String path = "/var/tmp/photo.jpeg";
        Location location = LocationDAO.getInstance().findLocationInAlbumByPath(album, path);
        assertNotNull(location,"location not found by album id and path");
        
        location = LocationDAO.getInstance().findLocationInAlbumByPath(album, "nosuchpath");
        
        assertNull(location,"location should be null");
        
    }
    
    @Override
    protected void prepareSettings() {
        System.out.println(">>> LocationDAOTest.prepareSettings");
        dataSetLocation = "org/smveloso/otof/em/locationDAOTestDS.xml";
        beforeTestOperations.add(DatabaseOperation.DELETE_ALL);
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
        afterTestOperations.add(DatabaseOperation.DELETE_ALL);
    }
    
    
    
    
}
