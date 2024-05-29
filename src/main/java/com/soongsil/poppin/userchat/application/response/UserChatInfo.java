package com.soongsil.poppin.userchat.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserChatInfo {
    private String popupName;

    @Builder
    public UserChatInfo(Long userId, String popupName) {
        this.popupName = popupName;
    }
}
