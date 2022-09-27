package com.payhere.gagebu.domain.user.exception;

import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;

import com.payhere.gagebu.common.exception.InvalidValueException;

public class UserAlreadyExistException extends InvalidValueException {

    public UserAlreadyExistException() {
        super(USER_ALREADY_EXIST);
    }
}
