package com.saransh.deliverytracker.controller.global;

import com.saransh.deliverytracker.ResponseModel.ErrorResponseModel;
import com.saransh.deliverytracker.exceptions.OrderRangeExceeded;
import com.saransh.deliverytracker.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * author: CryptoSingh1337
 */
@RestControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND)
                .contentType(APPLICATION_JSON)
                .body(ErrorResponseModel.builder()
                        .status(404)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(OrderRangeExceeded.class)
    public ResponseEntity<?> handleOrderRangeExceededException(OrderRangeExceeded e) {
        return ResponseEntity.status(BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body(ErrorResponseModel.builder()
                        .status(400)
                        .message(e.getMessage())
                        .build());
    }
}
