package com.payhere.gagebu.common.exception.handler;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payhere.gagebu.common.exception.BusinessException;
import com.payhere.gagebu.common.exception.EntityNotFoundException;
import com.payhere.gagebu.common.exception.InvalidPermissionException;
import com.payhere.gagebu.common.exception.InvalidValueException;
import com.payhere.gagebu.common.exception.error.ErrorMessage;
import com.payhere.gagebu.common.exception.error.ErrorResponse;
import com.payhere.gagebu.security.jwt.exception.TokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EXCEPTION_LOG_MESSAGE = "[EXCEPTION] {} : {}";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.info(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(TokenException.class)
    protected ResponseEntity<ErrorResponse> handleTokenException(TokenException e) {
        log.info(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.info(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(InvalidPermissionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPermissionException(InvalidPermissionException e) {
        log.info(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        log.info(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return new ErrorResponse(ErrorMessage.ACCESS_DENIED.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(InvalidValueException e) {
        log.info(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error(EXCEPTION_LOG_MESSAGE, e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorResponse(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage());
    }

}
