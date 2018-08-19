package org.smveloso.otof.em;

import java.util.List;
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
public class LocationDAOTest extends JpaBaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(LocationDAOTest.class);
    
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

        // case 1: there is a single association
        
        Album album = new LocalFileSystemAlbum(); album.setId(1000l);
        Photo photo = new Photo(); photo.setId(1000l);
        String path = "/var/tmp/photo.jpeg";
        List<Location> locations = LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo);
        assertNotNull(locations,"no locations found by album id and photo");
        assertEquals(locations.size(),1,"more than one location found, expetec just one");
        Location location = locations.get(0);
        
        assertEquals(location.getPath(),path,"path differs");
        assertNotNull(location.getAlbum(),"album is null for found location");
        assertEquals(location.getAlbum().getName(),"ALBUM ONE","album name is wrong");
        assertNotNull(location.getPhoto(),"photo is null for found location");
        assertEquals(location.getPhoto().getFileDigest(),"12345678901234567890","wrong digest for photo associated to location");
        
        // case 2: there is NO association

        album = new LocalFileSystemAlbum(); album.setId(1001l);
        photo = new Photo(); photo.setId(1001l);
        locations = LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo);
        assertTrue(locations.isEmpty(),"locations should be empty for there is no association between album and photo");
        
        // case 3: there is more than one association
        
        
        album = new LocalFileSystemAlbum(); album.setId(4000l);
        photo = new Photo(); photo.setId(4000l);
        locations = LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo);
        assertEquals(locations.size(),2,"two locations should have been found");
        
    }

    @Test(groups="jpa-test")
    public void testCreate() throws Exception {
        
        // case 1: album and photo exist, not associated to each other

        Album album = AlbumDAO.getInstance().findAlbum(1001l);
        Photo photo = PhotoDAO.getInstance().findFoto(1001l);
        
        assertNotNull(album,"cant test: album not found");
        assertNotNull(photo,"cant test: photo not found");
        assertTrue(LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo).isEmpty(),"cant test: association already exists");

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

    @Test(groups="jpa-test")
    public void testUpdate01() throws Exception {
    
        // case 1: existing entity (detached). update the 'path' only.
        
        Location location = LocationDAO.getInstance().findLocation(1000l);
        String EXPECTED_PATH = "/var/tmp/photo.jpeg";
        assertNotNull(location,"[1] cant test: null location");
        assertEquals(location.getPath(),EXPECTED_PATH,"[1] cant test: wrong original path");

        String NEW_PATH = "/new/path/photo.jpeg";
        location.setPath(NEW_PATH);
        LocationDAO.getInstance().update(location);
        assertEquals(location.getPath(),NEW_PATH,"[1] cant test: wrong updated path after update");
        location = LocationDAO.getInstance().findLocation(1000l);
        assertEquals(location.getPath(),NEW_PATH,"[1] cant test: wrong updated path after update and re-read");
        
    }

    /*
    @Test(groups="jpa-test")
    public void testUpdate02() throws Exception {
    
        // case 1: non-existing entity (new, with id). 
        //         associated to existing album and photo (both detached after read)
        //         update the 'path' only.

        String EXPECTED_PATH = "/var/tmp/photo.jpeg";                
        String NEW_PATH = "/the/new/path/photo.jpg";
        Long NEW_ID = 2000l;
        Long PHOTO_ID = 2000l;
        Long ALBUM_ID = 2000l;
        Location location = new Location();
        location.setId(NEW_ID);
        location.setPath(EXPECTED_PATH);
        Album album = AlbumDAO.getInstance().findAlbum(ALBUM_ID);
        Photo photo = PhotoDAO.getInstance().findFoto(PHOTO_ID);
        assertNotNull(album,"[2] cant test: null album");
        assertNotNull(photo,"[2] cant test: null photo");
        location.setAlbum(album);
        location.setPhoto(photo);

        LocationDAO.getInstance().update(location);
        
        location = LocationDAO.getInstance().findLocation(NEW_ID);
        assertNotNull(location,"[2] null location after update");
        
        
//        location.setPath(NEW_PATH);
//        LocationDAO.getInstance().update(location);
//        assertEquals(location.getPath(),NEW_PATH,"[1] cant test: wrong updated path after update");
//        location = LocationDAO.getInstance().findLocation(1000l);
//        assertEquals(location.getPath(),NEW_PATH,"[1] cant test: wrong updated path after update and re-read");


    }

    */

    @Test(groups="jpa-test")
    public void testPathUniquenessInAlbum() throws Exception {
        Location location = LocationDAO.getInstance().findLocation(3000l);
        assertNotNull(location,"cant test: no location");
        assertNotNull(location.getAlbum(),"cant test: null album");
        assertEquals(location.getPath(),"data/photo.jpeg","cant test: wrong location path");
        
        Location location2 = new Location();
        location2.setAlbum(location.getAlbum());
        location2.setPhoto(location.getPhoto());
        location2.setPath("a different path");
        
        LocationDAO.getInstance().create(location2);
        assertNotNull(location2.getId(),"new location, same alb and photo, different path, not created!");
        
        Location location3 = new Location();
        location3.setAlbum(location.getAlbum());
        location3.setPhoto(location.getPhoto());
        location3.setPath("data/photo.jpeg");
        
        try {
            LocationDAO.getInstance().create(location3);
            fail("Permitiu cadastrar nova location com path jah existente no album.");
        } catch (javax.persistence.PersistenceException expected) {
            logger.debug("EXPECTED: " + expected.getMessage());
            logger.trace("STACK:",expected);
        }
        
    }

    @Test(groups="jpa-test")
    public void testGetNumberOfPhotosInAllbum() throws Exception {
        Album album = new LocalFileSystemAlbum();
        album.setId(4000l);
        assertEquals(LocationDAO.getInstance().getNumberOfPhotosInAlbum(album), 1, "wrong count of photos in album (1)");
        album.setId(5000l);
        assertEquals(LocationDAO.getInstance().getNumberOfPhotosInAlbum(album), 2, "wrong count of photos in album (2)");
        album.setId(8000l);
        assertEquals(LocationDAO.getInstance().getNumberOfPhotosInAlbum(album), 20, "wrong count of photos in album (3)");
    }

    @Test(groups="jpa-test")
    public void testGetAllbumPhotos() throws Exception {
        logger.trace(">>> testGetAlbumPhoto()");
        
        Album album = new LocalFileSystemAlbum();
        album.setId(4000l);
        List<Photo> list = LocationDAO.getInstance().getAlbumPhotos(album, 0, 0);

        for (Photo photo:list) {
            logger.trace("PHOTO: " + photo.getId() + ":" + photo.getFileDigest());
        }
        
        assertEquals(list.size(), 1, "wrong count of photos in album");
        assertEquals(list.get(0).getId().longValue(),4000l,"wrong photo returned in list");
        
        album.setId(5000l);
        list = LocationDAO.getInstance().getAlbumPhotos(album, 0, 0);
        assertEquals(list.size(), 2, "wrong count of photos in album");
        assertEquals(list.get(0).getId().longValue(),5000l,"wrong photo returned in list (0)");
        assertEquals(list.get(1).getId().longValue(),6000l,"wrong photo returned in list (1)"); 
        
        album.setId(8000l);
        list = LocationDAO.getInstance().getAlbumPhotos(album, 0, 0);
        assertEquals(list.size(), 20, "wrong count of photos in album");
        
        list = LocationDAO.getInstance().getAlbumPhotos(album, 1, 5);
        assertEquals(list.size(), 5, "wrong count of photos in album");
        assertEquals(list.get(0).getId().intValue(), 8001, "wrong first photo in page (1)");

        list = LocationDAO.getInstance().getAlbumPhotos(album, 2, 5);
        assertEquals(list.size(), 5, "wrong count of photos in album");
        assertEquals(list.get(0).getId().intValue(), 8006, "wrong first photo in page (2)");

        list = LocationDAO.getInstance().getAlbumPhotos(album, 2, 5);
        assertEquals(list.size(), 5, "wrong count of photos in album");
        assertEquals(list.get(0).getId().intValue(), 8006, "wrong first photo in page (2)");

        // pagina nao existe
        list = LocationDAO.getInstance().getAlbumPhotos(album, 20, 5);
        assertEquals(list.size(), 0, "wrong count of photos in album (should be empty)");

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
