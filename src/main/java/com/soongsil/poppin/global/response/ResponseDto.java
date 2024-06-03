package com.soongsil.poppin.global.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> {
    private int statusCode;
    private String message;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private T data;


    @Builder
    public ResponseDto(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <T> ResponseDto<T> map(int statusCode, String message, T data) {
        return ResponseDto.<T>builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }
}
