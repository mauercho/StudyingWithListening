package com.ssafy.a304.shortgong.domain.summary.controller;

import static com.ssafy.a304.shortgong.global.errorCode.UploadContentErrorCode.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.summary.facade.SummaryFacade;
import com.ssafy.a304.shortgong.domain.uploadContent.model.constant.UploadContentType;
import com.ssafy.a304.shortgong.global.error.CustomException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summaries")
@Tag(name = "Summary API", description = "Summary management APIs")
public class SummaryController {

	private final SummaryFacade summaryFacade;

	/**
	 * 파일 업로드 및 요약본 생성
	 * */
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "multipart/form-data"})
	@Operation(summary = "create summary by File", description = "Create a Summary By File With Ocr")
	public ResponseEntity<?> createSummary(
		@RequestPart(value = "type") String type,
		@RequestPart(value = "contentFile", required = false) MultipartFile contentFile,
		@RequestPart(value = "url", required = false) String url
	) {

		if (type.equals(UploadContentType.IMAGE.getType())) {
			// TODO : contentFile 유효성 검사 ?
			return ResponseEntity.ok(summaryFacade.uploadContent(contentFile));
		} else if (type.equals(UploadContentType.URL.getType())) {
			// TODO : url 유효성 검사
			return ResponseEntity.ok(summaryFacade.uploadTextFileByUrl(url));
		}

		// if (!type.equals(UploadContentType.IMAGE.getType()) && !type.equals(UploadContentType.URL.getType())) {
		throw new CustomException(INVALID_UPLOAD_CONTENT_TYPE);
		// }
	}

	/**
	 * 요약본 상세 페이지 정보
	 * */
	@GetMapping("/{summary-id}")
	@Operation(summary = "Get summary Detail by Summary ID", description = "Returns a summary detail by summary id")
	public ResponseEntity<?> getSummaryDetail(@PathVariable("summary-id") Long summaryId) {

		return ResponseEntity.ok(summaryFacade.getSummaryDetail(summaryId));
	}

}
