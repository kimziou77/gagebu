package com.payhere.gagebu.security.jwt.exception;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class ExpiredAccessTokenException extends TokenException {

    public ExpiredAccessTokenException() {
        super(ErrorMessage.EXPIRED_ACCESS_TOKEN);
    }
}
