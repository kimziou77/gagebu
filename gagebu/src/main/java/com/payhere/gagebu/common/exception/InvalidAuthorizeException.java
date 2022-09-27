package com.payhere.gagebu.common.exception;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class InvalidAuthorizeException extends BusinessException {

    public InvalidAuthorizeException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
