package com.soongsil.poppin.userchat.application;

import com.soongsil.poppin.userchat.domain.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatSearchService {
    private final UserChatRepository userChatRepository;

    // 유저 채팅 리스트 불러오기
    public Page<String[]> getUserChatList(Long userId, Pageable pageable) {
        return userChatRepository.findUserChatListByUserId(userId, pageable);
    }
}
