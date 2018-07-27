package org.smveloso.otof.em;

import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author sergio
 */
public class AlbumDAOTest {
    
    public AlbumDAOTest() {
    }

    @Test
    public void testFindAlbumByName() throws Exception {
        AlbumDAO dao = AlbumDAO.getInstance();
        String name = "Test Album One";
        Album test = dao.findAlbumByName(name);
        assertNotNull(test,"Album nao encontrado por nome.");
        assertTrue(test.getClass().equals(LocalFileSystemAlbum.class));
    }

}
