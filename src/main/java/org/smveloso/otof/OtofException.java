package org.smveloso.otof;

/**
 *
 * @author sergiomv
 */
public class OtofException extends Exception {

    public OtofException() {
    }

    public OtofException(String message) {
        super(message);
    }

    public OtofException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtofException(Throwable cause) {
        super(cause);
    }

    public OtofException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
