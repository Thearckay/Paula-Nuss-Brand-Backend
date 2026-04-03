package com.paulanussbrand.store.controllers.controllerAdvice;

import com.paulanussbrand.store.dto.ResponseApi;
import com.paulanussbrand.store.exceptions.AuthException;
import com.paulanussbrand.store.exceptions.ProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ResponseApi> productException(ProductException exception){
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ResponseApi(
                        exception.getStatus(),
                        Collections.emptyList(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResponseApi> authException(AuthException exception){
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ResponseApi(
                        exception.getStatus(),
                        Collections.emptyList(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
