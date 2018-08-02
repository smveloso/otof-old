package org.smveloso.otof.util.digest;

import java.io.File;
import org.smveloso.otof.test.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DigestUtilTest {
    
    static TestUtil util = TestUtil.getInstance();
    
    public DigestUtilTest() {
    }
    
    @Test
    public void testGetSha1HexEncoded() throws Exception {
        System.out.println(">>> DigestUtilTest.testGetSha1HexEncoded");
        File arquivo = util.searchFileInClasspath("89db6573a0444b073bf6a454626e1816041e9424");
        Assert.assertTrue(arquivo.exists(),"Test file not found.");
        String expResult = "89db6573a0444b073bf6a454626e1816041e9424";
        String result = DigestUtil.getSha1HexEncoded(arquivo);
        Assert.assertEquals(expResult.toLowerCase(), result.toLowerCase());
    }
    
}
