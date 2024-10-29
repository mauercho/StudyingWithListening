package com.ssafy.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.ssafy.domain.lecture.handler.LectureChatStompHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	private final LectureChatStompHandler stompHandler; // jwt 인증

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// lecture 구독하고 구분할 예정 (강의마다 있으니까)
		config.enableSimpleBroker("/api/lecture-chat/sub"); // 메시지 구독 요청
		config.setApplicationDestinationPrefixes("/api/lecture-chat/send"); // 메세지를 발행하는 요청
	}

	// 웹소켓에 연결할 수 있는 엔드포인트
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// *로 일단 전체 설정해놨음
		registry.addEndpoint("/api/lecture-chat/connections").setAllowedOriginPatterns("*");
	}

	// STOMP 메시지를 가로채는 인터셉터를 등록: JWT 토큰을 검사하여 사용자의 인증 여부
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompHandler);
	}

}