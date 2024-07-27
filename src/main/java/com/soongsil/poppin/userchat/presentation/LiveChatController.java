package com.soongsil.poppin.userchat.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(String messageJson, @DestinationVariable String roomId) {
        try {
            System.out.println("받은 메시지 JSON: " + messageJson);

            ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);

            String id = UUID.randomUUID().toString();
            chatMessageDto.setId(id);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDateTime time = LocalDateTime.parse(chatMessageDto.getTime(), formatter);
            chatMessageDto.setTime(time.toString());

            System.out.println("보낸 메시지: " + chatMessageDto);

            messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, chatMessageDto);
        } catch (JsonProcessingException | DateTimeParseException e) {
            System.err.println("메시지 처리 중 오류 발생: " + e.getMessage());
        }
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(String messageJson, @DestinationVariable String roomId) throws JsonProcessingException {
        System.out.println("받은 메시지: " + messageJson);
        ChatMessageDto chatMessageDto = objectMapper.readValue(messageJson, ChatMessageDto.class);

        String id = UUID.randomUUID().toString();
        chatMessageDto.setId(id);

        System.out.println("보낸 메시지: " + chatMessageDto);

        messagingTemplate.convertAndSend("/sub/chat/room/" + roomId, chatMessageDto);
    }
}
