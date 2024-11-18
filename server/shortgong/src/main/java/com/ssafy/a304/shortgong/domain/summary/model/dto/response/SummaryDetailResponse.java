package com.ssafy.a304.shortgong.domain.summary.model.dto.response;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.SentenceTitle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SummaryDetailResponse {

	@Schema(description = "Summary Title", example = "My Summary")
	private final String summaryTitle;

	@Schema(description = "List of Sentence Titles")
	private final List<SentenceTitle> sentenceTitleList;

	@Schema(description = "List of Sentence Responses")
	private final List<SentenceResponse> sentenceResponseList;

	@Schema(description = "URL to the uploaded content", example = "https://example.com/content/123")
	private final String uploadContentUrl;

	@Builder
	SummaryDetailResponse(
		String summaryTitle,
		List<SentenceTitle> sentenceTitleList,
		List<SentenceResponse> sentenceResponseList,
		String uploadContentUrl
	) {

		this.summaryTitle = summaryTitle;
		this.sentenceTitleList = sentenceTitleList;
		this.sentenceResponseList = sentenceResponseList;
		this.uploadContentUrl = uploadContentUrl;
	}
}