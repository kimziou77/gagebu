package com.payhere.gagebu.domain.record.exception;

import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;

import com.payhere.gagebu.common.exception.EntityNotFoundException;

public class RecordNotFoundException extends EntityNotFoundException {

    public RecordNotFoundException() {
        super(RECORD_NOT_FOUND);
    }
}
