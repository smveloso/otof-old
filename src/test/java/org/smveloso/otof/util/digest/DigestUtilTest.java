package org.smveloso.otof.util.digest;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smveloso.otof.test.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DigestUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(DigestUtilTest.class);
    
    static TestUtil util = TestUtil.getInstance();
    
    public DigestUtilTest() {
    }
    
    @Test
    public void testGetSha1HexEncoded() throws Exception {
        logger.debug(">>> DigestUtilTest.testGetSha1HexEncoded");
        File arquivo = util.searchFileInClasspath("89db6573a0444b073bf6a454626e1816041e9424");
        Assert.assertTrue(arquivo.exists(),"Test file not found.");
        String expResult = "89db6573a0444b073bf6a454626e1816041e9424";
        String result = DigestUtil.getSha1HexEncoded(arquivo);
        Assert.assertEquals(expResult.toLowerCase(), result.toLowerCase());
    }
    
}
