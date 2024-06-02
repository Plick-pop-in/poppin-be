package com.soongsil.poppin.userchat.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate; // 추가
import org.springframework.stereotype.Controller;
import com.soongsil.poppin.userchat.application.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class LiveChatController {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate; // 추가

    public LiveChatController(ObjectMapper objectMapper, SimpMessagingTemplate messagingTemplate) { // 생성자에 SimpMessagingTemplate 추가
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(String messageJson) throws JsonProcessingException {
        System.out.println("받은 메시지: " + messageJson);
        ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);

        // 보낸 메시지에 대한 로그
        System.out.println("보낸 메시지: " + chatMessageDto);

        // 받은 메시지를 다시 클라이언트에게 보냄
        messagingTemplate.convertAndSend("/topic/public", chatMessageDto);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(String messageJson) throws JsonProcessingException {
        System.out.println("받은 메시지: " + messageJson);
        ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);

        // 보낸 메시지에 대한 로그
        System.out.println("보낸 메시지: " + chatMessageDto);

        // 받은 메시지를 다시 클라이언트에게 보냄
        messagingTemplate.convertAndSend("/topic/public", chatMessageDto);
    }

}


