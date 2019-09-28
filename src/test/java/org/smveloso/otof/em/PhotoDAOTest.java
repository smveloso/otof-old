package org.smveloso.otof.em;

import java.io.File;
import java.util.Date;
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
import org.smveloso.otof.util.date.DateUtil;
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
        int expResult = 3;
        int result = instance.getTotalPhotoCount();
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testCreatePhoto() throws Exception {
        logger.debug(Thread.currentThread().getId() + ">>>> PhotoDAOTest.testCreatePhoto");
        
        String digest = "101010101010";
        
        PhotoDAO instance = PhotoDAO.getInstance();
        Photo photo = new Photo();
        photo.setDateTaken(DateUtil.makeDate(2015, 1, 1, 14, 0, 0));
        photo.setFileDigest(digest);
        photo.setFileSize(1024l);
        instance.create(photo);
        
        Photo photo2 = instance.findFotoByDigest(digest, true);
        Assert.assertNotNull(photo2,"Foto nao encontrada por digest");

        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setContents(new byte[] {0,0,0});
        thumbnail.setWidth(100);
        thumbnail.setHeight(100);
        logger.debug("thumbnail created");        
        
        photo2.addThumbnail(thumbnail);
        instance.update(photo2);        
        
        Photo photo3 = instance.findFotoByDigest(digest, true);
        Assert.assertNotNull(photo3,"Foto nao encontrada por digest");
        Assert.assertNotNull(photo3.getThumbnails(), "Null thumbnails");
        Assert.assertFalse(photo3.getThumbnails().isEmpty(), "Empty thumbnails list");
        Assert.assertTrue(photo3.getThumbnails().size() == 1, "Wrong number of thumbnails");
        logger.trace("THUMB: " + photo3.getThumbnails().get(0).getId());
        
    
    }
    
    @Test
    public void testUpdatePhoto() throws Exception {
        logger.debug(Thread.currentThread().getId() + ">>>> PhotoDAOTest.testUpdatePhoto");
        PhotoDAO instance = PhotoDAO.getInstance();
        Photo photo = instance.findFotoByDigest("22222222222222222222");
        Assert.assertNotNull(photo,"Foto nao encontrada por digest");
        Assert.assertEquals(photo.getDateTaken().getTime(),DateUtil.makeDate(2010, 1, 1, 14, 0, 0).getTime(),"Wrong initial date in photo.");
        Date newDate = DateUtil.makeDate(2014,1,1,14,0,0);
        photo.setDateTaken(newDate);
        instance.update(photo);
        Photo newPhoto = instance.findFotoByDigest("22222222222222222222");
        Assert.assertNotNull(newPhoto,"Foto nao encontrada por digest");
        Assert.assertEquals(newPhoto.getDateTaken().getTime(),newDate.getTime(),"Wrong updated date in photo.");
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
        
        logger.debug("located photo");
        
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

        logger.debug("location is: " + path);
        
        Assert.assertTrue(testFile.exists(),"cant test: test file not found");
        Assert.assertTrue(testFile.isFile(),"cant test: test file not a file");
        Assert.assertTrue(testFile.canRead(),"cant test: test file not readable");

        int W = 200;
        int H = 200;
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setContents(DefaultThumbUtil.getInstance().makeRawThumb(testFile, W, H));
        thumbnail.setWidth(W);
        thumbnail.setHeight(H);
        
        logger.debug("thumbnail created");        
        
        //TODO should also test cascading and 'inverseness'
        Assert.assertTrue(photo.thumbnails.isEmpty(),"cant test: list of thumbs in photo should be empty");
        photo.addThumbnail(thumbnail);
        
        logger.debug("about to update photo ...");        
        PhotoDAO.getInstance().update(photo);
        logger.debug("updated photo");        
        
        // now we query for it ... must improve this ...
        
        Photo storedPhoto = PhotoDAO.getInstance().findFoto(1000l,true);
        Assert.assertNotNull(storedPhoto,"photo null");
        Assert.assertNotNull(storedPhoto.thumbnails,"thumb set is null");
        Assert.assertFalse(storedPhoto.thumbnails.isEmpty(),"thumb set is empty");
        
        logger.debug("<<< testThumbnail1()");

    }

    @Test
    public void testThumbnail2() throws Exception {
        logger.debug(">>> testThumbnail2()");
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
        logger.debug("<<< testThumbnail2()");
    }    

    @Test
    public void testThumbnail3() throws Exception {
        logger.debug(">>> testThumbnail3()");
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
                
        logger.debug("<<< testThumbnail3()");
    }    

    @Test
    public void testThumbnail4() throws Exception {
        logger.debug(">>> testThumbnail4()");
        PhotoDAO instance = PhotoDAO.getInstance();
        Photo photo = instance.findFoto(1001l,true);
                
        Assert.assertNotNull(photo.thumbnails,"null thumbs");
        Assert.assertFalse(photo.thumbnails.isEmpty(),"empty thumbs");
        Assert.assertEquals(photo.thumbnails.size(),1, "should have exactly one thumb");
        Assert.assertEquals((long) photo.thumbnails.get(0).getId(),1l,"id not as expected");
        
        Thumbnail thumbnail = instance.findThumbnail(1l);
        Assert.assertNotNull(thumbnail,"Thumbnail not found before photo destruction.");
        
        instance.destroyThumbnail(1l);
        
        thumbnail = instance.findThumbnail(1l);
        Assert.assertNull(thumbnail,"Thumbnail found after destruction.");
        
        // photo must still be there !!!!
        photo = instance.findFoto(1001l,true);
        Assert.assertNotNull(photo,"null photo");        
        Assert.assertTrue(photo.thumbnails.isEmpty(),"expected empty thumbs after thumb removal");
                
        logger.debug("<<< testThumbnail4()");
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
