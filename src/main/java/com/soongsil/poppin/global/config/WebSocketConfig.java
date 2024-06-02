package com.soongsil.poppin.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "*") // 모든 요청에 대해 CORS를 허용합니다. 필요에 따라 원하는 origin을 지정할 수 있습니다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // 메시지 브로커 설정
        config.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 메시지를 수신할 엔드포인트 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // STOMP 엔드포인트 등록
                .setAllowedOrigins("*");
    }
}


