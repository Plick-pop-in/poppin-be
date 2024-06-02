package com.soongsil.poppin.userchat.presentation;

import com.fasterxml.jackson.databind.ObjectMapper; // ObjectMapper 임포트 추가
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.beans.factory.annotation.Autowired; // Autowired 임포트 추가
import com.soongsil.poppin.userchat.application.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException; // JsonProcessingException 임포트 추가

@Controller
public class LiveChatController {

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper 자동 주입

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(String messageJson) throws JsonProcessingException {
        System.out.println("받은 메시지: " + messageJson); // 메시지 출력
        ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);
        return chatMessageDto;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDto addUser(String messageJson) throws JsonProcessingException {
        System.out.println("받은 메시지: " + messageJson); // 메시지 출력
        ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);
        return chatMessageDto;
    }
}


