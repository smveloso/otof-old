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
        assertNotNull(location.getPhoto(),"photo is null for found location");
        assertEquals(location.getPhoto().getFileDigest(),"12345678901234567890","wrong digest for photo associated to location");
        
        location = LocationDAO.getInstance().findLocationInAlbumByPath(album, "nosuchpath");
        assertNull(location,"location should be null");
        
    }

    @Test(groups="jpa-test")
    public void testFindLocationByAlbumAndPhoto() throws Exception {

        // case 1: there is an association
        
        Album album = new LocalFileSystemAlbum(); album.setId(1000l);
        Photo photo = new Photo(); photo.setId(1000l);
        String path = "/var/tmp/photo.jpeg";
        Location location = LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo);
        assertNotNull(location,"location not found by album id and photo");
        assertEquals(location.getPath(),path,"path differs");
        assertNotNull(location.getAlbum(),"album is null for found location");
        assertEquals(location.getAlbum().getName(),"ALBUM ONE","album name is wrong");
        assertNotNull(location.getPhoto(),"photo is null for found location");
        assertEquals(location.getPhoto().getFileDigest(),"12345678901234567890","wrong digest for photo associated to location");
        
        // case 2: there is NO association

        album = new LocalFileSystemAlbum(); album.setId(1001l);
        photo = new Photo(); photo.setId(1001l);
        location = LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo);
        assertNull(location,"location should be null for there is no association between album and photo");
        
    }

    @Test(groups="jpa-test")
    public void testCreate() throws Exception {
        
        // case 1: album and photo exist, not associated to each other

        Album album = AlbumDAO.getInstance().findAlbum(1001l);
        Photo photo = PhotoDAO.getInstance().findFoto(1001l);
        
        assertNotNull(album,"cant test: album not found");
        assertNotNull(photo,"cant test: photo not found");
        assertNull(LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo),"cant test: association already exists");

        //TODO create de location album (ou photo) "new" irá criá-los (album e/ou photo) ?
        //TODO verificar as propagacoes de criacao e update e delete na associacoes (cascade e etc ...)
        
        String NEW_LOCATION_PATH = "/the/path/photo2.jpg";
        Location location = new Location();
        location.setAlbum(album);
        location.setPhoto(photo);
        location.setPath(NEW_LOCATION_PATH);
        LocationDAO.getInstance().create(location);

        System.out.println("NEW LOCATION ID: " + location.getId());
        assertNotNull(location,"new location is null");
        assertNotNull(location.getId(),"null id for location just created");
        assertNotNull(location.getAlbum(),"album is null in just-created location");
        assertNotNull(location.getPhoto(),"photo is null in just-created location");
        assertNotNull(location.getPath(),"path is null in just-created location");

        assertNotNull(LocationDAO.getInstance().findLocationInAlbumByPath(album, NEW_LOCATION_PATH),"cant find new location by album and path");
        assertNotNull(LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo),"cant find new location by album and photo");
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
