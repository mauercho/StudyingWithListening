package com.ssafy.domain.lecture.service;

import java.time.LocalDateTime;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.lecture.model.dto.request.LectureChatRequest;
import com.ssafy.domain.lecture.model.dto.response.LectureChatResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureChatServiceImpl implements LectureChatService {
	private final SimpMessageSendingOperations messagingTemplate;

	@Override
	public void sendLectureChat(Long lectureId, LectureChatRequest chat) {
		messagingTemplate.convertAndSend("/api/lecture-chat/sub/" + lectureId,
			LectureChatResponse.builder()
				.sender(chat.getSender())
				.message(chat.getMessage())
				.sendingTime(LocalDateTime.now())
				.build()
		);
	}

}
