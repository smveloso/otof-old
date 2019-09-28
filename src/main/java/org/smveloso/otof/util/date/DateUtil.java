package org.smveloso.otof.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 
     * @param yr
     * @param mon The month (jan == 1)
     * @param day
     * @param hr
     * @param min
     * @param sec
     * @return A java.util.Date object with 0 millis
     */
    public static Date makeDate(int yr, int mon, int day, int hr, int min, int sec) {
        Calendar cal = Calendar.getInstance();
        cal.set(yr, (mon - 1), day, hr, min, sec);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
}
