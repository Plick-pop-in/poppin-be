package com.soongsil.poppin.userchat.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserChatInfo {
    private Long userId;
    private String popupName;

    @Builder
    public UserChatInfo(Long userId, String popupName) {
        this.userId = userId;
        this.popupName = popupName;
    }
}
