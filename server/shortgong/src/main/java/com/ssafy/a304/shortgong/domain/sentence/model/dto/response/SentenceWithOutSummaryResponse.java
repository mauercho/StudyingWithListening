package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentenceWithOutSummaryResponse {

	private Long id;

	private String sentenceContent;

	private String voiceFileName;

	private Integer order;

	private String createdAt;

	private Boolean openStatus;

	public static SentenceWithOutSummaryResponse from(Sentence sentence) {

		return SentenceWithOutSummaryResponse.builder()
			.id(sentence.getId())
			.sentenceContent(sentence.getSentenceContent())
			.voiceFileName(sentence.getVoiceFileName())
			.order(sentence.getOrder())
			.createdAt(sentence.getCreatedAt().toString())
			.openStatus(sentence.getOpenStatus())
			.build();
	}
}
