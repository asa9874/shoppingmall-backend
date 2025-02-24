package com.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle SellerNotFoundException
    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<String> handleSellerNotFound(SellerNotFoundException ex) {
        log.error("Seller not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found: " + ex.getMessage());
    }

    // Handle InvalidProductDataException
    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<String> handleInvalidProductData(InvalidProductDataException ex) {
        log.error("Invalid product data: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product data: " + ex.getMessage());
    }

    // Handle generic exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedError(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
    }
}
