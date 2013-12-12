package org.smveloso.otof.exinf;

import org.smveloso.otof.OtofException;

/**
 *
 * @author sergiomv
 */
public class ExinfException extends OtofException {

    public ExinfException() {
    }

    public ExinfException(String message) {
        super(message);
    }

    public ExinfException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExinfException(Throwable cause) {
        super(cause);
    }

    public ExinfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
