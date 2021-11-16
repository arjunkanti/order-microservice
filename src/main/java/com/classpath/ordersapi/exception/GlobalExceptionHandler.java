package com.classpath.ordersapi.exception;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleInvalidOrderId(IllegalArgumentException exception){
        return ResponseEntity.status(NOT_FOUND).body(new Error(100, exception.getMessage()));
    }
}

@Data
class Error{
    private final int code;
    private final String message;
}