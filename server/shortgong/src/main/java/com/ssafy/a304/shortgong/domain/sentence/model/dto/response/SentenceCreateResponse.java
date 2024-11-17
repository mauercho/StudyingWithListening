package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

import lombok.Builder;
import lombok.Getter;

/**
 * 요약집의 퀴즈(Q&A) 정보를 담는 응답 dto
 * @author 정재영
 */
@Getter
@Builder
public class SentenceCreateResponse {

	private Long id;

	private String sentencePoint;

	private String sentenceContentNormal;

	private String sentenceContentSimple;

	private String normalVoiceFileName;

	private String simpleVoiceFileName;

	private Integer order;

	private String createdAt;

	private Boolean openStatus;

	public static SentenceCreateResponse from(Sentence sentence) {

		return SentenceCreateResponse.builder()
			.id(sentence.getId())
			.sentencePoint(sentence.getPoint())
			.sentenceContentNormal(sentence.getSentenceContentNormal())
			.sentenceContentSimple(sentence.getSentenceContentSimple())
			.normalVoiceFileName(sentence.getNormalVoiceFileName())
			.simpleVoiceFileName(sentence.getSimpleVoiceFileName())
			.order(sentence.getOrder())
			.createdAt(sentence.getCreatedAt().toString())
			.openStatus(sentence.getOpenStatus())
			.build();
	}
}
