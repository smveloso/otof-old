package org.smveloso.otof.util.digest;

import org.smveloso.otof.OtofException;

/**
 *
 * @author sergiomv
 */
public class DigestUtilException extends OtofException {

    public DigestUtilException() {
    }

    public DigestUtilException(String message) {
        super(message);
    }

    public DigestUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public DigestUtilException(Throwable cause) {
        super(cause);
    }

    public DigestUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
