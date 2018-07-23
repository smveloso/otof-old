package org.smveloso.otof.em;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        int expResult = 0;
        int result = instance.getTotalPhotoCount();
        assertEquals(expResult, result);
    }
    
}
