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

    @JsonProperty("roomId")
    private String roomId;

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and Setter for content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and Setter for sender
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // Getter and Setter for time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

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
