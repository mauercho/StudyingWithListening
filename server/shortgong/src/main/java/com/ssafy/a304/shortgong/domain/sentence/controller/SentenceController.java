package com.ssafy.a304.shortgong.domain.sentence.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a304.shortgong.domain.sentence.facade.SentenceFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sentences")
public class SentenceController {

	private final SentenceFacade sentenceFacade;

	@PatchMapping("/{sentence-id}")
	private ResponseEntity<?> RecreateSentence(@PathVariable("sentence-id") Long sentenceId) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(sentenceFacade.executeGptApi(sentenceId));
	}

}
