package com.paulanussbrand.store.exceptions;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {
    private Integer status;
    public AuthException(String message, Integer status) {
        super(message);
        setStatus(status);
    }

    public AuthException(String message, HttpStatus httpStatus){
        super(message);
        this.status = httpStatus.value();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
