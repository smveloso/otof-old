package org.smveloso.otof.em;

import org.smveloso.otof.model.Photo;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PhotoDAOTest {

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
        System.out.println(">>>> PhotoDAOTest.getTotalPhotoCount");
        PhotoDAO instance = PhotoDAO.getInstance();
        int expResult = 1;
        int result = instance.getTotalPhotoCount();
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testFindFotoByDigest()  throws Exception {
        System.out.println(">>> PhotoDAOTest.testFindFotoByDigest");
        PhotoDAO instance = PhotoDAO.getInstance();
        String digest = "12345678901234567890";
        Photo photo = instance.findFotoByDigest(digest);
        Assert.assertNotNull(photo,"Foto nao encontrada por digest.");
        
    }
    
}
