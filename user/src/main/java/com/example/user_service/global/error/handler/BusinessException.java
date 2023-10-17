package com.example.user_service.global.error.handler;

import com.example.etc_service.global.error.ErrorCode;

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -7099930022202674652L;
    private ErrorCode errorCode;
    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
