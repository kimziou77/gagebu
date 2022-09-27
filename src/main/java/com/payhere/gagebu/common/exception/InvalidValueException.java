package com.payhere.gagebu.common.exception;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class InvalidValueException extends BusinessException {
    
    public InvalidValueException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
