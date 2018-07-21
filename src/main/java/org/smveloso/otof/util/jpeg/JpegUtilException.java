package org.smveloso.otof.util.jpeg;

import org.smveloso.otof.OtofException;

/**
 *
 * @author sergiomv
 */
public class JpegUtilException extends OtofException {

    public JpegUtilException() {
    }

    public JpegUtilException(String message) {
        super(message);
    }

    public JpegUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public JpegUtilException(Throwable cause) {
        super(cause);
    }

    public JpegUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
