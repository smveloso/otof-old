package org.smveloso.otof.em;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.Location;
import org.smveloso.otof.model.Photo;
import org.smveloso.otof.model.Thumbnail;
import org.smveloso.otof.test.JpaBaseTest;
import org.smveloso.otof.util.thumb.DefaultThumbUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PhotoDAOTest extends JpaBaseTest {

    private static final Logger logger = LoggerFactory.getLogger(PhotoDAOTest.class);
    
    // Neste sistema, a hibernatefactory é alocada
    // implicitamente (vide DAO e JpaManager.
    
    // Nao parece ser necessário usar um @Before{Class,Group,...)
    // para ativar o mecanismo ORM.

    // Por outro lado, o banco de dados sofre
    // alterações externas entre os testes.
    
    public PhotoDAOTest() {
    }
    
    @Test
    public void testGetTotalPhotoCount() {
        logger.debug(Thread.currentThread().getId() + ">>>> PhotoDAOTest.getTotalPhotoCount");
        PhotoDAO instance = PhotoDAO.getInstance();
        int expResult = 2;
        int result = instance.getTotalPhotoCount();
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testFindFotoByDigest()  throws Exception {
        logger.debug(Thread.currentThread().getId() + ">>> PhotoDAOTest.testFindFotoByDigest");
        PhotoDAO instance = PhotoDAO.getInstance();
        String digest = "12345678901234567890";
        Photo photo = instance.findFotoByDigest(digest);
        logger.debug("FOTO IS NULL ? " + (null == photo));
        Assert.assertNotNull(photo,"Foto nao encontrada por digest.");
        
    }

    @Test
    public void testThumbnail1() throws Exception {
        logger.debug(">>> testThumbnail1()");
        PhotoDAO instance = PhotoDAO.getInstance();
        Photo photo = instance.findFoto(1000l,true);
        
        Assert.assertNotNull(photo,"cant test: photo not found by id.");
        
        // does it point to a valid location ?
        
        //TODO: (start) refactor after merging branch 'SEARCH': use LocationDAO.findPhotoLocations        
        Album album = AlbumDAO.getInstance().findAlbum(1000l);
        Assert.assertNotNull(album, "cant test: album not found");
        Assert.assertEquals(album.getName(),"PHOTOTEST ALBUM","cant test: wrong album");
        List<Location> locations = LocationDAO.getInstance().findLocationInAlbumByPhoto(album, photo);
        //TODO: (end) refactor after merging branch 'SEARCH': use LocationDAO.findPhotoLocations        

        Assert.assertNotNull(locations,"cant test: null list of locations");
        Assert.assertFalse(locations.isEmpty(),"cant test: empty list of locations");
        Assert.assertFalse(locations.size() > 1,"should not test: multiple locations not expected");
                
        String path = locations.get(0).getPath();
        File testFile = new File(path);
        
        Assert.assertTrue(testFile.exists(),"cant test: test file not found");
        Assert.assertTrue(testFile.isFile(),"cant test: test file not a file");
        Assert.assertTrue(testFile.canRead(),"cant test: test file not readable");

        int W = 200;
        int H = 200;
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setContents(DefaultThumbUtil.getInstance().makeRawThumb(testFile, W, H));
        thumbnail.setWidth(W);
        thumbnail.setHeight(H);
        
        //TODO should also test cascading and 'inverseness'
        Assert.assertTrue(photo.thumbnails.isEmpty(),"cant test: list of thumbs in photo should be empty");
        photo.thumbnails = new ArrayList<>();
        photo.thumbnails.add(thumbnail);
        
        PhotoDAO.getInstance().update(photo);

        // now we query for it ... must improve this ...
        
        Photo storedPhoto = PhotoDAO.getInstance().findFoto(1000l,true);
        Assert.assertNotNull(storedPhoto,"photo null");
        Assert.assertNotNull(storedPhoto.thumbnails,"thumb set is null");
        Assert.assertFalse(storedPhoto.thumbnails.isEmpty(),"thumb set is empty");
        
        logger.debug("<<< testThumbnail1()");

    }

    @Test
    public void testThumbnail2() throws Exception {
        logger.debug(">>> testThumbnail12()");
        PhotoDAO instance = PhotoDAO.getInstance();
        Photo photo = instance.findFoto(1001l);
        
        try {
            photo.thumbnails.size();
            Assert.fail("A 'lazy' exception was expected here.");
        } catch (LazyInitializationException expected) {
        }
        
        photo = instance.findFoto(1001l, true);
        
        Assert.assertNotNull(photo.thumbnails,"null thumbs");
        Assert.assertFalse(photo.thumbnails.isEmpty(),"empty thumbs");
        Assert.assertEquals(photo.thumbnails.size(),1, "should have exactly one thumb");
        
        Assert.assertNotNull(photo,"cant test: photo not found by id.");
        logger.debug("<<< testThumbnail12()");
    }    

    @Test
    public void testThumbnail3() throws Exception {
        logger.debug(">>> testThumbnail13()");
        PhotoDAO instance = PhotoDAO.getInstance();
        Photo photo = instance.findFoto(1001l,true);
                
        Assert.assertNotNull(photo.thumbnails,"null thumbs");
        Assert.assertFalse(photo.thumbnails.isEmpty(),"empty thumbs");
        Assert.assertEquals(photo.thumbnails.size(),1, "should have exactly one thumb");
        Assert.assertEquals((long) photo.thumbnails.get(0).getId(),1l,"id not as expected");
        
        Thumbnail thumbnail = instance.findThumbnail(1l);
        Assert.assertNotNull(thumbnail,"Thumbnail not found before photo destruction.");
        
        instance.destroy(1001l);
        
        photo = instance.findFoto(1001l,true);
        Assert.assertNull(photo,"photo not null after destruction");
        
        thumbnail = instance.findThumbnail(1l);
        Assert.assertNull(thumbnail,"Thumbnail after before photo destruction.");
                
        logger.debug("<<< testThumbnail13()");
    }    

    
    @Override
    protected void prepareSettings() {
        logger.debug(Thread.currentThread().getId() + ">>> PhotoDAOTest.prepareSettings");
        dataSetLocation = "org/smveloso/otof/em/photoDAOTestDS.xml";
        beforeTestOperations.add(DatabaseOperation.DELETE_ALL);
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
        afterTestOperations.add(DatabaseOperation.DELETE_ALL);    
    }
    
}
