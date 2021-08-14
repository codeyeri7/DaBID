package com.ssafy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //WebSocket을 통해 브로커 메시징을 활성하기 위해 사용한다
public class WebSockConfig implements WebSocketMessageBrokerConfigurer { //WebSocketMessageBrokerConfigurer : WebSocket 클라이언트로 받은 메세지 핸들링을 구성하는 메소드들이 정의할때 사용한다

    @Override
    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
    public void configureMessageBroker(MessageBrokerRegistry registry) { //MessageBrokerRegistry : 메세지 브로커 옵션을 설정한다
        registry.enableSimpleBroker("/sub"); // subscribe : /sub로 시작하는 목적지를 가진 메세지가 메세지브로커로 라우팅한다,topic으로 시작되는 메시지가 메세지브로커로 라우팅됨
        registry.setApplicationDestinationPrefixes("/pub"); // publish : destination이 /pub으로 된 메세지를 @Controller의 @MessageMapping으로 라우팅한다,message-handling methods로 라우팅됨
    }

    @Override
    // connection을 맺을때 CORS 허용합니다.

    //클라이언트가 웹 소켓 서버에 연결하는 데 사용할 웹 소켓 엔드 포인트를 등록한다
    //엔드 포인트 구성에 withSockJS ()를 사용한다
    //SockJS는 웹 소켓을 지원하지 않는 브라우저에 폴백 옵션을 활성화하는 데 사용된다
    public void registerStompEndpoints(StompEndpointRegistry registry) { // 이 링크로 동작하는 socket js를 만들어 등록하겠다.
//        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*").withSockJS(); // "/ws" 는 WebSocket 클라이언트가 Handshake를 위해 연결해야하는 end-point의 url
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();

    }

}
