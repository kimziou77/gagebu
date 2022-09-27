package com.payhere.gagebu.domain.record.exception;

import static com.payhere.gagebu.common.exception.error.ErrorMessage.*;

import com.payhere.gagebu.common.exception.InvalidPermissionException;

public class RecordNoPermissionException extends InvalidPermissionException {

    public RecordNoPermissionException() {
        super(ACCESS_DENIED);
    }

}
