package com.example.AnVD_project.until.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AbstractError> handleBusinessEException(BusinessException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .extras(ex.getExtras())
                .httpStatus(ex.getHttpStatus())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
}
