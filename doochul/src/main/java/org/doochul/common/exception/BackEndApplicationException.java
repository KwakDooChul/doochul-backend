package org.doochul.common.exception;

import org.springframework.http.HttpStatus;

public class BackEndApplicationException extends RuntimeException {

    private final ErrorCodes errorCodes;
    private final HttpStatus status;


    public BackEndApplicationException(ErrorCodes errorCodes, HttpStatus status) {
        this.errorCodes = errorCodes;
        this.status = status;
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
