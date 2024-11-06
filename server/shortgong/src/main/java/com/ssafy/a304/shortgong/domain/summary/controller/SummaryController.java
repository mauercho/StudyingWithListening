package com.ssafy.a304.shortgong.domain.summary.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/**
	 * 요약본 목록 가져오기
	 * */
	@GetMapping
	public ResponseEntity<?> getSummaryList() {

		return ResponseEntity.ok(summaryFacade.getSummaryList());
	}

	/**
	 * 파일 업로드 및 요약본 생성
	 * */
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "multipart/form-data"})
	public ResponseEntity<?> createSummary(
		@RequestPart(value = "contentFile", required = false) MultipartFile contentFile
	) {

		return ResponseEntity.ok(summaryFacade.uploadContent(contentFile));
	}

	/**
	 * 요약본 상세 페이지 정보
	 * */
	@GetMapping("/{summary-id}")
	public ResponseEntity<?> getSummaryDetail(@PathVariable("summary-id") Long summaryId) {

		return ResponseEntity.ok(summaryFacade.getSummaryDetail(summaryId));
	}

	/**
	 * 요약본 제목 변경
	 * */
	@PatchMapping("/{summary-id}")
	public ResponseEntity<?> modifySummaryTitle(@PathVariable("summary-id") Long summaryId,
		@RequestBody String title) {

		summaryFacade.updateTitleBySummaryId(title, summaryId);
		return ResponseEntity.ok().build();
	}

}
