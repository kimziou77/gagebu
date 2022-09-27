package com.payhere.gagebu.domain.user.exception;

import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;

import com.payhere.gagebu.common.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}

