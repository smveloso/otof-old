package org.smveloso.otof.exinf;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sergiomv
 */
public class ExinfFacade {
    
    public static Date getDataTirada(File file) {
        return Calendar.getInstance().getTime();
    }
    
}
