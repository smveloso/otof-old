package org.smveloso.otof.em;

import org.smveloso.otof.model.Photo;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PhotoDAOTest {
    
    public PhotoDAOTest() {
    }
    
    @BeforeClass
    public void setUp() {
    }

    @Test
    public void testGetTotalPhotoCount() {
        System.out.println("getTotalPhotoCount");
        PhotoDAO instance = PhotoDAO.getInstance();
        int expResult = 1;
        int result = instance.getTotalPhotoCount();
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testFindFotoByDigest()  throws Exception {
        PhotoDAO instance = PhotoDAO.getInstance();
        String digest = "12345678901234567890";
        Photo photo = instance.findFotoByDigest(digest);
        Assert.assertNotNull(photo,"Foto nao encontrada por digest.");
        
    }
    
}
