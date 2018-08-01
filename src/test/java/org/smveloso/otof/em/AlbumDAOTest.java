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
    
    // Neste sistema, a hibernatefactory é alocada
    // implicitamente (vide DAO e JpaManager.
    
    // Nao parece ser necessário usar um @Before{Class,Group,...)
    // para ativar o mecanismo ORM.

    // Por outro lado, o banco de dados sofre
    // alterações externas entre os testes.
    
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

    @Test
    public void testCreateAlbum() throws Exception {
        
        String mp = "/var/album/";
        String nm = "the album";
        
        AlbumDAO dao = AlbumDAO.getInstance();
        LocalFileSystemAlbum album = new LocalFileSystemAlbum();
        album.setMountPointAsString(mp);
        album.setName(nm);
        dao.create(album);
        
        Long id = album.getId();
        assertNotNull(id);

        // by id ...
        Album retrievedAlbumById = dao.findAlbum(id);
        assertNotNull(retrievedAlbumById);
        assertEquals(retrievedAlbumById.getName(),nm);
        assertTrue(retrievedAlbumById.getClass().equals(LocalFileSystemAlbum.class));
        assertEquals(((LocalFileSystemAlbum) retrievedAlbumById).getMountPointAsString(), mp);
        
        // by name ...
        Album retrievedAlbumByName = dao.findAlbumByName(nm);
        assertNotNull(retrievedAlbumByName);
        assertEquals(retrievedAlbumByName.getName(),nm);
        assertTrue(retrievedAlbumByName.getClass().equals(LocalFileSystemAlbum.class));
        assertEquals(((LocalFileSystemAlbum) retrievedAlbumByName).getMountPointAsString(), mp);
        
    }
    
}
