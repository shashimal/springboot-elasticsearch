package com.duleendra.exception;

/**
 * Exceptions in service functions are handled here
 *
 *
 * @author Duleendra Shashimal
 *
 */
public class AppServiceException extends Exception {

    private static final long serialVersionUID = -1182225774881347447L;

    private String statusCode;
    private String statusMsg;
    private String logMessage;

    public AppServiceException() {
        super();
    }

    public AppServiceException(String logMessage, Throwable cause) {
        super(logMessage, cause);
    }

    public AppServiceException(String statusCode, String statusMsg, String logMessage, Throwable cause) {
        super(logMessage, cause);
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public AppServiceException(String message) {
        super(message);
    }

    public AppServiceException(Throwable cause) {
        super(cause);
    }
}
