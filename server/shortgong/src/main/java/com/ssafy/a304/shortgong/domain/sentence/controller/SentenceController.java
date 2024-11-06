package com.ssafy.a304.shortgong.domain.sentence.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a304.shortgong.domain.sentence.facade.SentenceFacade;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.request.SentenceModifyRequest;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.request.SentenceUpdateOpenStatusRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sentences")
public class SentenceController {

	private final SentenceFacade sentenceFacade;

	@PatchMapping("/{sentence-id}")
	private ResponseEntity<?> modifySentence(@PathVariable("sentence-id") Long sentenceId,
		@Valid @RequestBody SentenceModifyRequest sentenceModifyRequest) {

		return ResponseEntity.ok(sentenceFacade.modifySentence(sentenceId, sentenceModifyRequest));
	}

	@PostMapping("/{sentence-id}")
	private ResponseEntity<?> updateSentenceOpenStatus(@PathVariable("sentence-id") Long sentenceId,
		@Valid @RequestBody SentenceUpdateOpenStatusRequest sentenceUpdateOpenStatusRequest) {

		sentenceFacade.updateSentenceOpenStatus(sentenceId, sentenceUpdateOpenStatusRequest);
		return ResponseEntity.ok().build();
	}

}
