package com.payhere.gagebu.common.exception.handler;

import static org.springframework.http.HttpStatus.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payhere.gagebu.common.exception.InvalidPermissionException;
import com.payhere.gagebu.common.exception.error.ErrorMessage;
import com.payhere.gagebu.common.exception.error.ErrorResponse;

@RestControllerAdvice
public class ForbiddenExceptionHandler {

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(InvalidPermissionException.class)
    public ErrorResponse handleInvalidPermissionException(InvalidPermissionException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        return new ErrorResponse(ErrorMessage.ACCESS_DENIED.getMessage());
    }

}
