package org.smveloso.otof.em;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.smveloso.otof.model.Photo;

public class PhotoDAOTest {
    
    public PhotoDAOTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testGetTotalPhotoCount() {
        System.out.println("getTotalPhotoCount");
        PhotoDAO instance = PhotoDAO.getInstance();
        int expResult = 1;
        int result = instance.getTotalPhotoCount();
        assertEquals(expResult, result);
    }

    @Test
    public void testFindFotoByDigest()  throws Exception {
        PhotoDAO instance = PhotoDAO.getInstance();
        String digest = "12345678901234567890";
        Photo photo = instance.findFotoByDigest(digest);
        assertNotNull("Foto nao encontrada por digest.",photo);
        
    }
    
}
