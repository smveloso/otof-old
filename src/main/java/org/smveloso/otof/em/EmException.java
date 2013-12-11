package org.smveloso.otof.em;

import org.smveloso.otof.OtofException;

/**
 *
 * @author sergiomv
 */
public class EmException extends OtofException {

    public EmException() {
    }

    public EmException(String message) {
        super(message);
    }

    public EmException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmException(Throwable cause) {
        super(cause);
    }

    public EmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
