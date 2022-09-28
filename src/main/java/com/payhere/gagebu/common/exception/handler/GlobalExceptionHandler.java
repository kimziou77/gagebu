package com.payhere.gagebu.common.exception.handler;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payhere.gagebu.common.exception.BusinessException;
import com.payhere.gagebu.common.exception.error.ErrorResponse;
import com.payhere.gagebu.security.jwt.exception.TokenException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(TokenException.class)
    protected ResponseEntity<ErrorResponse> handleTokenException(TokenException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
    }

}
