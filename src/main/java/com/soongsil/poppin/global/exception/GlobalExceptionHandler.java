package com.soongsil.poppin.global.exception;

import com.soongsil.poppin.global.response.ErrorCode;
import com.soongsil.poppin.global.response.ErrorResponseDto;
import com.soongsil.poppin.popup.application.exception.PopupException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 추가한 커스텀 예외 처리를 정의하면 Handler 를 추가 작성
    @ExceptionHandler(PopupException.class)
    public ResponseEntity<ErrorResponseDto> handleUserException(PopupException e) {
        log.error("PopupException : {}", e.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.map(e.getErrorCode()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException e) {
        log.error("BusinessException : {}", e.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.map(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto>handleException(Exception e) {
        log.error("Exception : {}", e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.map(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
