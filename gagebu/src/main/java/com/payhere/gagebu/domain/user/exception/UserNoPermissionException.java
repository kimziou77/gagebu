package com.payhere.gagebu.domain.user.exception;

import com.payhere.gagebu.common.exception.InvalidAuthorizeException;
import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class UserNoPermissionException extends InvalidAuthorizeException {

    public UserNoPermissionException() {
        super(ErrorMessage.LOGIN_FAILED);
    }
}
