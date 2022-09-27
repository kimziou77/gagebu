package com.payhere.gagebu.security.jwt.exception;

import com.payhere.gagebu.common.exception.BusinessException;
import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class TokenException extends BusinessException {

    public TokenException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
