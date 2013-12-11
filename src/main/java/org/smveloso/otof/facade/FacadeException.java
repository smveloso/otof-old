package org.smveloso.otof.facade;

import org.smveloso.otof.OtofException;

/**
 *
 * @author sergiomv
 */
public class FacadeException extends OtofException {

    public FacadeException() {
    }

    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacadeException(Throwable cause) {
        super(cause);
    }

    public FacadeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
