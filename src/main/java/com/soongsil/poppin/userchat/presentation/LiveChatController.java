package com.soongsil.poppin.userchat.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.soongsil.poppin.userchat.application.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Controller
public class LiveChatController {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public LiveChatController(ObjectMapper objectMapper, SimpMessagingTemplate messagingTemplate) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload String messageJson) {
        try {
            System.out.println("받은 메시지 JSON: " + messageJson);

            // 메시지를 ChatMessageDto 객체로 변환
            ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);

            // 아이디 생성 및 세팅
            String id = UUID.randomUUID().toString();
            chatMessageDto.setId(id);

            // 형식 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime time = LocalDateTime.parse(chatMessageDto.getTime(), formatter);
            chatMessageDto.setTime(time.toString());

            System.out.println("보낸 메시지: " + chatMessageDto);

            // 메시지를 특정 채팅방으로 전송
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
        } catch (JsonProcessingException | DateTimeParseException e) {
            System.err.println("메시지 처리 중 오류 발생: " + e.getMessage());
        }
    }

    @MessageMapping("/chat/addUser")
    public void addUser(@Payload String messageJson) throws JsonProcessingException {
        System.out.println("받은 메시지: " + messageJson);
        ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);

        // UUID 생성
        String id = UUID.randomUUID().toString();
        chatMessageDto.setId(id);

        // 보낸 메시지에 대한 로그
        System.out.println("보낸 메시지: " + chatMessageDto);

        // 받은 메시지를 다시 클라이언트에게 보냄
        messagingTemplate.convertAndSend("/sub/public", chatMessageDto);
    }
}
