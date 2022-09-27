package com.payhere.gagebu.common.exception;

import com.payhere.gagebu.common.exception.error.ErrorMessage;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(ErrorMessage.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorMessage message) {
        super(message);
    }
}
