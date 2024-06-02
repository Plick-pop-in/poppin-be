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

    @Override
    public String toString() {
        return "ChatMessageDto{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
