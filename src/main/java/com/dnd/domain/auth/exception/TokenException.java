package com.dnd.domain.auth.exception;

import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;

public class TokenException extends CustomException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
