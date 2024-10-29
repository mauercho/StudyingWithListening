package com.ssafy.domain.ai_talk.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.ai_talk.model.dto.request.AiTalkRequest;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkResponse;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkSituationResponse;
import com.ssafy.domain.ai_talk.service.AiTalkService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai-talk")
public class AiTalkController {
	private final AiTalkService talkService;

	@GetMapping("/situation")
	public ResponseEntity<?> getAllSituation(@RequestParam(value = "topic", required = false) String topic) {
		List<AiTalkSituationResponse> aiTalkSituationResponses;

		if (topic != null && !topic.isEmpty()) {
			aiTalkSituationResponses = talkService.getAiTalkSituationByTopic(topic);
		} else {
			aiTalkSituationResponses = talkService.getAllAiTalkSituations();
		}

		if (aiTalkSituationResponses.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(aiTalkSituationResponses);
		}
	}

	@Operation(summary = "해당 situation에서 회화 시작")
	@GetMapping("/start")
	public ResponseEntity<AiTalkResponse> createChat(@RequestParam("situation") Long situationId) {
		return ResponseEntity.status(HttpStatus.OK).body(talkService.setSituation(situationId));
	}

	@Operation(summary = "AI 회화 메세지 전송")
	@PostMapping
	public ResponseEntity<?> sendMessage(@Valid @RequestBody AiTalkRequest aiTalkRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(talkService.getAiResponse(aiTalkRequest));
	}

	@Operation(summary = "AI 회화 끝내기")
	@GetMapping("/finish")
	public ResponseEntity<?> closeChat() {
		return ResponseEntity.status(HttpStatus.OK).body(talkService.finishAiResponse());
	}
}
