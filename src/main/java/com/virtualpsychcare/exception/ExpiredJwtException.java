package com.virtualpsychcare.exception;

public class ExpiredJwtException extends Throwable {

    public ExpiredJwtException() {
        super();
    }

    public ExpiredJwtException(String message) {
        super(message);
    }

    public ExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredJwtException(Throwable cause) {
        super(cause);
    }
}
