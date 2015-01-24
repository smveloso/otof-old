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
    public void testDetermineSeparator() {
        
        Organizador org = new Organizador();
        
        assertEquals("Letter drive should be windows.", "\\", org.determinePathSeparator("c:\\teste\\teste.txt"));
        assertEquals("Cap letter drive should be windows.", "\\", org.determinePathSeparator("Z:\\teste\\teste.txt"));
        assertEquals("UNC style should be windows", "\\", org.determinePathSeparator("\\\\teste\\teste.txt"));
        
        assertEquals("Full linux path should be linux", "/", org.determinePathSeparator("/home/teste/teste.txt"));

        assertEquals("Partial windows path should be windows", "\\", org.determinePathSeparator("windowsdir\\windowssubdir\\file.txt"));

        assertEquals("Partial linux path should be linux", "/", org.determinePathSeparator("linuxdir/linuxsubdir/file.txt"));

        assertEquals("Filename only should assume system's separator", File.separator, org.determinePathSeparator("file.txt"));
       
    }
    
    @Test
    public void testExtractNameFromPath() {
    
        Organizador org = new Organizador();
        
        String path;
                
        path = "c:\\windows\\path\\test\\file.txt";
        assertEquals("Wrong for windows drive-letter path: ", "file.txt", org.extractNameFromPath(path));

        path = "C:\\windows\\path\\test\\file.txt";
        assertEquals("Wrong for windows cap drive-letter path: ", "file.txt", org.extractNameFromPath(path));

        path = "\\\\server\\windows\\path\\test\\file.txt";
        assertEquals("Wrong for windows UNC path: ", "file.txt", org.extractNameFromPath(path));

        path = "/home/teste/file.txt";
        assertEquals("Wrong for full linux path: ", "file.txt", org.extractNameFromPath(path));
        
        
        path = "windowsdir\\windowssubdir\\file.txt";
        assertEquals("Wrong for partial windows path: ", "file.txt", org.extractNameFromPath(path));
        
        path = "linuxdir/linuxsubdir/file.txt";
        assertEquals("Wrong for partial Linux path: ", "file.txt", org.extractNameFromPath(path));

        path = "file.txt";
        assertEquals("Failed for filename only. ", "file.txt", org.extractNameFromPath(path));
    
    }
}
