package com.example.webzine.gw.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exp) {
        ApiResponse res = ApiResponse.builder()
                .code(500)
                .message(exp.getMessage())
        .build();

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
