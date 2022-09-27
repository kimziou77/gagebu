package com.payhere.gagebu.common.exception;

import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class InvalidPermissionException extends BusinessException {

    public InvalidPermissionException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public InvalidPermissionException() {
        super(ACCESS_DENIED);
    }
}
