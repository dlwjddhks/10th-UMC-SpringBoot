package com.example.umc10th.domain.user.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;

public class UserException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public UserException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }
}