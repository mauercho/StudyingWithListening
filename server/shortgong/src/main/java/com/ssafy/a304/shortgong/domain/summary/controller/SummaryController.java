package com.ssafy.a304.shortgong.domain.summary.controller;

import static com.ssafy.a304.shortgong.domain.uploadContent.model.constant.UploadContentType.*;
import static com.ssafy.a304.shortgong.global.errorCode.UploadContentErrorCode.*;

import java.util.Map;

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
import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryDetailResponse;
import com.ssafy.a304.shortgong.global.error.CustomException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summaries")
@Tag(name = "Summary API", description = "Summary management APIs")
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
	@PostMapping(consumes = "multipart/form-data")
	@Operation(summary = "create summary by File", description = "Create a Summary By File With Ocr")
	public ResponseEntity<?> createSummary(
		@RequestPart(value = "type") String type,
		@RequestPart(value = "contentFile", required = false) MultipartFile contentFile,
		@RequestPart(value = "url", required = false) String url,
		@RequestPart(value = "keyword", required = false) String keyword
	) {

		return switch (fromType(type)) {
			case IMAGE -> ResponseEntity.ok(summaryFacade.uploadContentByFile(contentFile));
			case URL -> ResponseEntity.ok(summaryFacade.uploadTextFileByUrl(url));
			case KEYWORD -> ResponseEntity.ok(summaryFacade.uploadByKeyword(keyword));
			default -> throw new CustomException(INVALID_UPLOAD_CONTENT_TYPE);
		};

	}

	/**
	 * 요약본 상세 페이지 정보
	 * */
	@GetMapping("/{summary-id}")
	@Operation(
		summary = "Get summary Detail by Summary ID",
		description = "Returns a summary detail by summary id",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Successful response",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = SummaryDetailResponse.class)
				)
			),
			@ApiResponse(
				responseCode = "404",
				description = "Summary not found"
			)
		}
	)
	public ResponseEntity<?> getSummaryDetail(@PathVariable("summary-id") Long summaryId) {

		return ResponseEntity.ok(summaryFacade.getSummaryDetail(summaryId));
	}

	/**
	 * 요약본 제목 변경
	 * */
	@PatchMapping("/{summary-id}")
	public ResponseEntity<?> modifySummaryTitle(
		@PathVariable("summary-id") Long summaryId,
		@RequestBody Map<String, String> requestBody
	) {

		String title = requestBody.get("title");
		return ResponseEntity.ok(summaryFacade.updateTitleBySummaryId(title, summaryId).getTitle());
	}

}
