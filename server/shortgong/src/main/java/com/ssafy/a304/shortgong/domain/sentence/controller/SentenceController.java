package com.ssafy.a304.shortgong.domain.sentence.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a304.shortgong.domain.sentence.facade.SentenceFacade;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.request.SentenceModifyRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sentences")
public class SentenceController {

	private final SentenceFacade sentenceFacade;

	@PatchMapping("/{sentence-id}")
	public ResponseEntity<?> modifySentence(@PathVariable("sentence-id") Long sentenceId,
		@Valid @RequestBody SentenceModifyRequest sentenceModifyRequest) {

		return ResponseEntity.ok(sentenceFacade.modifySentence(sentenceId, sentenceModifyRequest));
	}

	/**
	 * 문장 펼치기
	 * @param sentenceId (문장 id)
	 * @return None
	 */
	@PostMapping("/{sentence-id}/open-status")
	public ResponseEntity<?> updateSentenceOpenStatusToOpen(@PathVariable("sentence-id") Long sentenceId) {

		sentenceFacade.changeSentenceStatusToOpen(sentenceId);
		return ResponseEntity.ok().build();
	}

	/**
	 * 문장 접기
	 * @param sentenceId (문장 id)
	 * @return None
	 */
	@DeleteMapping("/{sentence-id}/open-status")
	public ResponseEntity<?> updateSentenceOpenStatusToClose(@PathVariable("sentence-id") Long sentenceId) {

		sentenceFacade.changeSentenceStatusToClose(sentenceId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{sentence-id}")
	public ResponseEntity<?> deleteSentence(@PathVariable("sentence-id") Long sentenceId) {

		sentenceFacade.deleteSentence(sentenceId);
		return ResponseEntity.ok().build();
	}

}
