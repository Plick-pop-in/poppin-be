package com.soongsil.poppin.global.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버 내부 에러입니다."),

    // 401
    INVALID_TOKEN(401, "잘못된 토큰 요청"),
    EXPIRED_TOKEN(401, "토큰 만료되었습니다. 토큰 재발행 혹은 로그인을 다시 해주세요"),

    // 400
    INVALID_METHOD_ARGUMENT(400, "잘못된 인자입니다."),
    NULL_VALUE_ERROR(400, "Null 값이 올 수 없습니다."),
    INVALID_REQUEST(400, "request 정보를 읽을 수 없습니다."),
    ENUM_NOT_FOUND(400, "존재하지 않는 ENUM 값입니다."),
    EMAIL_DUPLICATE(400, "이미 존재하는 이메일입니다."),
    NICKNAME_DUPLICATE(400, "이미 존재하는 닉네임입니다."),
    INVALID_PASSWORD(400, "올바르지 않은 비밀번호입니다."),


    // 404
    USER_NOT_FOUND(404, "존재하지 않는 계정입니다."),
    POPUP_NOT_FOUND(404, "존재하지 않는 팝업입니다."),
    POPUP_IMG_NOT_FOUND(404, "존재하지 않는 팝업 이미지 입니다."),

    // 405
    METHOD_NOT_ALLOWED(405, "대상 리소스가 이 메서드를 지원하지 않습니다."),
    ;

    private final int errorCode;
    private final String message;

    ErrorCode(int errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
