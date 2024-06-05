package com.soongsil.poppin.user.application.exception;

import com.soongsil.poppin.global.exception.BusinessException;
import com.soongsil.poppin.global.response.ErrorCode;

public class UserException extends BusinessException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
