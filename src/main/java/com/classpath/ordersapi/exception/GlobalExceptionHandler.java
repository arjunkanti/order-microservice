package com.classpath.ordersapi.exception;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleInvalidOrderId(IllegalArgumentException exception){
        return ResponseEntity.status(NOT_FOUND).body(new Error(100, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOrderInput(MethodArgumentNotValidException exception){

        final List<String> errors = exception
                                            .getAllErrors()
                                            .stream()
                                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                            .collect(Collectors.toList());

        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("code", 400);
        errorResponse.put("errors", errors);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
     }
}

@Data
class Error{
    private final int code;
    private final String message;
}