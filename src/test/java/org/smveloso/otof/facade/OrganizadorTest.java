package org.smveloso.otof.facade;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sergio
 */
public class OrganizadorTest {
    
    public OrganizadorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testExtractNameFromPath() {
    
        Organizador org = new Organizador();
        
        String pathSep = File.pathSeparator;
        String windowsSep = "\\";
        String linuxSep = "/";
        
        String path;
                
        path = "C:\\windows\\path\\test\\file.txt";
        assertEquals("Wrong for windows path: ", "file.txt", org.extractNameFromPath(path));

        /* TODO open issue on bitbucket to support any separator
        path = "/home/path/file.txt";
        assertEquals("Wrong for linux path: ", "file.txt", org.extractNameFromPath(path));                
        */        
    }
}
