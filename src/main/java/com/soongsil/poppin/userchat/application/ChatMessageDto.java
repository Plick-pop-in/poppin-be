package com.soongsil.poppin.userchat.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessageDto {
    @JsonProperty("type")
    private String type;

    @JsonProperty("content")
    private String content;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("time")
    private String time;

    @JsonProperty("id")
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // 다른 필드들의 getter, setter, toString 등은 생략

    @Override
    public String toString() {
        return "ChatMessageDto{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
