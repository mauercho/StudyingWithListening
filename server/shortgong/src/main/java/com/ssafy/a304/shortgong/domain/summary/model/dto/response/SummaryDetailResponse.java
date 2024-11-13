package com.ssafy.a304.shortgong.domain.summary.model.dto.response;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.SentenceTitle;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SummaryDetailResponse {

	private final String summaryTitle;
	private final List<SentenceTitle> sentenceTitleList; // 목차
	private final List<SentenceResponse> sentenceResponseList;

	@Builder
	SummaryDetailResponse(
		String summaryTitle,
		List<SentenceTitle> sentenceTitleList,
		List<SentenceResponse> sentenceResponseList) {

		this.summaryTitle = summaryTitle;
		this.sentenceTitleList = sentenceTitleList;
		this.sentenceResponseList = sentenceResponseList;
	}
}
