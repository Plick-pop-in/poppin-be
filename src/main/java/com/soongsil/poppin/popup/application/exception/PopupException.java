package com.soongsil.poppin.popup.application.exception;

import com.soongsil.poppin.global.exception.BusinessException;
import com.soongsil.poppin.global.response.ErrorCode;

public class PopupException extends BusinessException {
    public PopupException(ErrorCode errorCode) {
        super(errorCode);
    }
}
