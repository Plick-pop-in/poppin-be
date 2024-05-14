package com.soongsil.poppin.userchat.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserChatList {
    private final List<Integer> popupUserChats;
}
