package com.soongsil.poppin.userchat.presentation;

import com.soongsil.poppin.userchat.application.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LiveChatController { // 클래스 이름을 LiveChatController로 변경

    @MessageMapping("/chat.sendMessage") // 클라이언트에서 채팅 메시지를 전송할 때 사용되는 엔드포인트
    @SendTo("/topic/public") // 채팅 메시지를 구독하는 모든 클라이언트에게 메시지를 브로드캐스트
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser") // 클라이언트가 채팅에 참여할 때 사용되는 엔드포인트
    @SendTo("/topic/public") // 새로운 사용자가 채팅에 참여했음을 모든 클라이언트에게 알림
    public ChatMessage addUser(ChatMessage chatMessage) {
        return chatMessage;
    }
}
