package com.soongsil.poppin.userchat.application;

import com.soongsil.poppin.userchat.application.response.UserChatInfo;
import com.soongsil.poppin.userchat.domain.UserChat;
import com.soongsil.poppin.userchat.domain.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserChatSearchService {
    private final UserChatRepository userChatRepository;

    // 유저 채팅 리스트 불러오기
    public List<UserChatInfo> getUserChats(int page, int size, Long userId){
        List<UserChatInfo> userChats = new ArrayList<>();

        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page, size);

        // 유저가 가진 채팅 불러오기
        Page<UserChat> userChatPage = userChatRepository.findUserChatsByMemberUserId(userId, pageable);
        List<UserChat> userChatList = userChatPage.getContent();

        // UserChat 엔티티를 UserChatInfo로 변환하여 리스트에 추가
        for (UserChat userChat : userChatList) {
            UserChatInfo userChatInfo = UserChatInfo.builder()
                    .userId(userChat.getMember().getUserId()) // userId 추가
                    .popupName(userChat.getPopup().getPopupName())
                    .build();
            userChats.add(userChatInfo);
        }

        return userChats;
    }
}
