package org.smveloso.otof.util.jpeg;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.smveloso.otof.test.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JpegUtilTest {
    
    static TestUtil util = TestUtil.getInstance();

    
    public JpegUtilTest() {
    }

    @Test
    public void testGetDataTirada() throws Exception {
        System.out.println(">>> JpegUtilTest.testGetDataTirada");
        File file = util.searchFileInClasspath("foto-1.jpg");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("BRST"));
        cal.set(Calendar.YEAR,2008);
        cal.set(Calendar.MONTH,Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH,18);
        Date expResult = cal.getTime();
        Date result = JpegUtil.getDataTirada(file);
        // apenas a data
        Assert.assertTrue(util.compareDateOnly(result, expResult));
    }
    
}
