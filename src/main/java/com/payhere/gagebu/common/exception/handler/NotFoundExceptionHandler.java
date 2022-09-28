package com.payhere.gagebu.common.exception.handler;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payhere.gagebu.common.exception.EntityNotFoundException;
import com.payhere.gagebu.common.exception.error.ErrorResponse;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

}
