package com.ssafy.domain.sentence.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.review.model.dto.response.ReviewSentenceResponse;
import com.ssafy.domain.sentence.model.dto.request.SentenceCreateRequest;
import com.ssafy.domain.sentence.model.dto.request.SentenceTestRequest;
import com.ssafy.domain.sentence.model.dto.response.SentenceDictationResponse;
import com.ssafy.domain.sentence.model.dto.response.SentenceTestLeavesResponse;
import com.ssafy.domain.sentence.service.SentenceService;
import com.ssafy.domain.user.service.StudyTimeService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sentences")
public class SentenceController {

	private final SentenceService sentenceService;
	private final StudyTimeService studyTimeService;

	@Operation(summary = "받아쓰기 문장 조회")
	@GetMapping
	public ResponseEntity<?> getDictationSentence(@RequestParam(value = "topic", required = false) String topic) {
		List<SentenceDictationResponse> sentenceList = sentenceService.getRandomSentence(topic);
		if (sentenceList == null || sentenceList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(sentenceList);
		}
	}

	@Operation(summary = "받아쓰기 채점")
	@PostMapping("writing-test")
	public ResponseEntity<?> testWritingPapers(@Valid @RequestBody List<SentenceTestRequest> sentenceTestAnswers) {
		SentenceTestLeavesResponse sentenceTestLeavesResponse = sentenceService.testWritingPaper(sentenceTestAnswers);
		studyTimeService.increaseDictationCount(sentenceTestAnswers.size());
		return ResponseEntity.status(HttpStatus.OK).body(sentenceTestLeavesResponse);
	}

	@Operation(summary = "사용자 문장 생성")
	@PostMapping
	public ResponseEntity<?> createSentence(@Valid @RequestBody SentenceCreateRequest sentenceCreateRequest) {
		ReviewSentenceResponse reviewSentenceResponse = sentenceService.createSentence(sentenceCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewSentenceResponse);
	}

}
