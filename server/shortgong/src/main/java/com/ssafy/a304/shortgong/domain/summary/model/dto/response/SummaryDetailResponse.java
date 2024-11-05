package com.ssafy.a304.shortgong.domain.summary.model.dto.response;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SummaryDetailResponse {

	private final String summaryTitle;
	// TODO : 목차 필드 생성
	private final List<SentenceResponse> sentenceResponseList;

	@Builder
	SummaryDetailResponse(String summaryTitle, List<SentenceResponse> sentenceResponseList) {

		this.summaryTitle = summaryTitle;
		this.sentenceResponseList = sentenceResponseList;
	}
}
