package com.soongsil.poppin.userchat.application;

import com.soongsil.poppin.popup.domain.Popup;
import com.soongsil.poppin.popup.domain.PopupRepository;
import com.soongsil.poppin.user.domain.Member;
import com.soongsil.poppin.user.domain.UserRepository;
import com.soongsil.poppin.userchat.application.response.JoinUserChat;
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
    private final UserRepository userRepository;
    private final PopupRepository popupRepository;

    // 유저 채팅 리스트 불러오기
    public List<UserChatInfo> getUserChats(int page, int size, Long userId){
        List<UserChatInfo> userChats = new ArrayList<>();

        // 유저가 가진 채팅 불러오기
        List<UserChat> userChatList = userChatRepository.findUserChatsByMemberUserId(userId);

        // UserChat 엔티티를 UserChatInfo로 변환하여 리스트에 추가
        for (UserChat userChat : userChatList) {
            UserChatInfo userChatInfo = UserChatInfo.builder()
                    .popupName(userChat.getPopup().getPopupName())
                    .build();
            userChats.add(userChatInfo);
        }

        return userChats;
    }

    public JoinUserChat joinLive(Long userId, Long popupId, Integer minusPoint){
        JoinUserChat joinUserChat =null;
        //참여하고 있는 채팅방인지 확인
        Boolean isJoin = userChatRepository.existsByMemberUserIdAndPopupPopupId(userId, popupId);

        //이미 참여 하고 있다면
        if(isJoin) {
            joinUserChat = new JoinUserChat(isJoin,null, popupId, null);
            return joinUserChat;
        }

        // 포인트 충분한지 확인
        Long userPoint = userRepository.getPointById(userId);
        Long leftPoint = userPoint - minusPoint;
        if(leftPoint < 0){     //포인트 부족하다면
            joinUserChat = new JoinUserChat(isJoin, false, popupId, null);
            return joinUserChat;
        }


        userRepository.updatePayPoint(userId,leftPoint); //채팅 방에서 값 차감

        Member member = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Popup popup = popupRepository.findById(popupId).orElseThrow(() -> new IllegalArgumentException("Invalid popup ID"));

        UserChat userChat = UserChat.builder()
                .member(member)
                .popup(popup)
                .price(minusPoint)
                .build();

        userChatRepository.save(userChat);
        joinUserChat = new JoinUserChat(isJoin, true, popupId, leftPoint);


        return joinUserChat;
    }
}
