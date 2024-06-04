package com.soongsil.poppin.userchat.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinUserChat {
    private final Boolean isJoin;   //이미 가입 되어있는지?
    private final Boolean isMoney;  //돈이 있는지?
    private final Long popupId;
    private final Long leftPoint;

    @Builder
    public JoinUserChat(Boolean isJoin, Boolean isMoney, Long popupId, Long leftPoint) {
        this.isJoin =isJoin;
        this.isMoney =isMoney;
        this.popupId = popupId;
        this.leftPoint= leftPoint;
    }

    public Boolean getJoin() {
        return isJoin;
    }

    public Boolean getMoney() {
        return isMoney;
    }

}
