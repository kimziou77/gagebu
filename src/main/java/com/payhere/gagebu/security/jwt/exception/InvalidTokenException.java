package com.payhere.gagebu.security.jwt.exception;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class InvalidTokenException extends TokenException {

    public InvalidTokenException() {
        super(ErrorMessage.INVALID_TOKEN);
    }
}
