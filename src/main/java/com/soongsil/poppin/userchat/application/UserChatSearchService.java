package com.soongsil.poppin.userchat.application;

import com.soongsil.poppin.userchat.domain.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatSearchService {
    private final UserChatRepository userChatRepository;

    // 유저 채팅 리스트 불러오기

}
