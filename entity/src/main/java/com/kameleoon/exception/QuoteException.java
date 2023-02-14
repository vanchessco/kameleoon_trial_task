package com.kameleoon.exception;

import org.springframework.http.HttpStatus;

public class QuoteException extends RuntimeException {

    private HttpStatus status;

    public QuoteException(String message) {
        super(message);
    }

    public QuoteException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public QuoteException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public QuoteException(String message, Throwable cause) {
        super(message, cause);
    }


    public String errorMessage() {
        return status.value() + ":".concat(getMessage());
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
