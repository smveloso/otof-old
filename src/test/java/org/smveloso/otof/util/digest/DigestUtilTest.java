package org.smveloso.otof.util.digest;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.smveloso.otof.test.TestUtil;

public class DigestUtilTest {
    
    static TestUtil util = TestUtil.getInstance();
    
    public DigestUtilTest() {
    }
    
    @Test
    public void testGetSha1HexEncoded() throws Exception {
        File arquivo = util.searchFileInClasspath("89db6573a0444b073bf6a454626e1816041e9424");
        assertTrue("Test file not found.", arquivo.exists());
        String expResult = "89db6573a0444b073bf6a454626e1816041e9424";
        String result = DigestUtil.getSha1HexEncoded(arquivo);
        assertEquals(expResult.toLowerCase(), result.toLowerCase());
    }
    
}
