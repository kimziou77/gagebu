package com.payhere.gagebu.common.exception;

import org.springframework.http.HttpStatus;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.httpStatus = errorMessage.getStatus();
    }

}
