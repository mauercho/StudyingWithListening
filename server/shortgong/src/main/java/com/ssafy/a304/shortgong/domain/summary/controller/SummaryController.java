package com.ssafy.a304.shortgong.domain.summary.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.summary.facade.SummaryFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summaries")
public class SummaryController {

	private final SummaryFacade summaryFacade;

	/*
	 * 파일 업로드 및 요약본 생성
	 * */
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "multipart/form-data"})
	public ResponseEntity<?> createSummary(
		@RequestPart(value = "contentFile", required = false) MultipartFile contentFile
	) {

		return ResponseEntity.ok(summaryFacade.uploadContent(contentFile));
	}

}