package com.dnd.domain.auth.exception;

import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;

public class ExpiredTokenException extends CustomException {
    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
