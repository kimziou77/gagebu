package com.payhere.gagebu.common.exception.handler;

import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.payhere.gagebu.common.exception.InvalidValueException;
import com.payhere.gagebu.common.exception.error.ErrorResponse;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ErrorResponse handleBindException(BindException e) {
        return ErrorResponse.of(INVALID_INPUT.getMessage(), e.getBindingResult());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ErrorResponse.of(INVALID_TYPE.getMessage(), e);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public ErrorResponse handleInvalidValueException(InvalidValueException e) {
        return new ErrorResponse(e.getMessage());
    }
}
