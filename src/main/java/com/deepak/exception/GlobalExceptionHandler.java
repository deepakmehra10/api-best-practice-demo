package com.deepak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException productNotFoundException,
                                                                        HttpServletRequest httpServletRequest) {
        String error = "Not Found";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(error)
                .status(HttpStatus.NOT_FOUND.value())
                .path(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .message(productNotFoundException.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = ProductAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExistException(ProductAlreadyExistException productAlreadyExistException,
                                                                        HttpServletRequest httpServletRequest) {
        String error = "Conflict";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(error)
                .status(HttpStatus.CONFLICT.value())
                .path(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .message(productAlreadyExistException.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
