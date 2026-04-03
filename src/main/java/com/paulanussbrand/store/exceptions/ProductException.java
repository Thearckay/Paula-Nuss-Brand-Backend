package com.paulanussbrand.store.exceptions;

import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException {
    private Integer status;

    public ProductException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public ProductException(String message, HttpStatus status) {
        super(message);
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
