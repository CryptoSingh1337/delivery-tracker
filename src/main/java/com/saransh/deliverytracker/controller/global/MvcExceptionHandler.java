package com.saransh.deliverytracker.controller.global;

import com.saransh.deliverytracker.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * author: CryptoSingh1337
 */
@RestControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
}
