package com.ssafy.domain.lecture.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ssafy.global.auth.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LectureChatStompHandler implements ChannelInterceptor {
	private final JwtTokenProvider jwtTokenProvider;

	// STOMP 메시지가 채널로 전송되기 전에 호출되는 메서드: 메시지를 가로채어 처리
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		// STOMP 메시지의 헤더 정보를 다루는 클래스로 헤더 정보 추출
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		// 클라이언트가 WebSocket 연결을 시도할 때만 검증을 수행
		if (accessor.getCommand() == StompCommand.CONNECT) {
			String bearerToken = accessor.getFirstNativeHeader("Authorization");
			if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
				String token = bearerToken.substring(7);
				if (!jwtTokenProvider.validateToken(token)) {
					throw new AccessDeniedException("유효하지 않은 토큰으로 접근이 차단되었습니다.");
				}
			} else {
				throw new AccessDeniedException("Authorization 헤더가 잘못되었습니다.");
			}
		}
		return message;
	}

}
