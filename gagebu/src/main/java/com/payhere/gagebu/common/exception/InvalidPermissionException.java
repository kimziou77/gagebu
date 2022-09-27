package com.payhere.gagebu.common.exception;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class InvalidPermissionException extends BusinessException {

    public InvalidPermissionException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
