package com.dnd.domain.auth.exception;

import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
