package org.smveloso.otof.em;

import org.dbunit.operation.DatabaseOperation;
import org.smveloso.otof.model.Album;
import org.smveloso.otof.model.LocalFileSystemAlbum;
import org.smveloso.otof.test.JpaBaseTest;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author sergio
 */
public class AlbumDAOTest extends JpaBaseTest {
    
    // Neste sistema, a hibernatefactory é alocada
    // implicitamente (vide DAO e JpaManager.
    
    // Nao parece ser necessário usar um @Before{Class,Group,...)
    // para ativar o mecanismo ORM.

    // Por outro lado, o banco de dados sofre
    // alterações externas entre os testes.
    
    public AlbumDAOTest() {
    }

    @Test(groups="jpa-test")
    public void testFindAlbumByName() throws Exception {
        System.out.println(">>> AlbumDAOTest.testFindAlbumByName");
        AlbumDAO dao = AlbumDAO.getInstance();
        String name = "Test Album One";
        Album test = dao.findAlbumByName(name);
        assertNotNull(test,"Album nao encontrado por nome.");
        assertTrue(test.getClass().equals(LocalFileSystemAlbum.class));
        assertTrue(((LocalFileSystemAlbum) test).getMountPointAsString().equals("/the/path"));
    }
        
    @Test(groups="jpa-test")
    public void testCreateAlbum() throws Exception {
        System.out.println(">>> AlbumDAOTest.testCreateAlbum");
        
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

    @Override
    protected void prepareSettings() {
        System.out.println(">>> AlbumDAOTest.prepareSettings");
        dataSetLocation = "org/smveloso/otof/em/albumDAOTestDS.xml";
        beforeTestOperations.add(DatabaseOperation.DELETE_ALL);
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
        afterTestOperations.add(DatabaseOperation.DELETE_ALL);
    }
    
}
