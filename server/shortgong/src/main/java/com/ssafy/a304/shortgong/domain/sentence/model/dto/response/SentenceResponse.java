package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.global.util.S3FileUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SentenceResponse {

	private final Long id;
	private final Long sentenceTitleId;
	private final String sentencePoint;

	private final Integer order;
	private final boolean openStatus;

	private final String contentNormal;
	private final String contentDetail;
	private final String contentSimple;

	private final String normalVoiceFileName;
	private final String detailVoiceFileName;
	private final String simpleVoiceFileName;

	@Builder
	SentenceResponse(Sentence sentence) {

		this.id = sentence.getId();
		this.sentenceTitleId = sentence.getSentenceTitle().getId();
		this.sentencePoint = sentence.getSentencePoint();

		this.order = sentence.getOrder();
		this.openStatus = sentence.getOpenStatus();

		this.contentNormal = sentence.getSentenceContentNormal();
		this.contentDetail = sentence.getSentenceContentDetail();
		this.contentSimple = sentence.getSentenceContentSimple();

		this.normalVoiceFileName = S3FileUtil.getPreSignedUrl(sentence.getNormalVoiceFileName());
		this.simpleVoiceFileName = S3FileUtil.getPreSignedUrl(sentence.getSimpleVoiceFileName());
		this.detailVoiceFileName = S3FileUtil.getPreSignedUrl(sentence.getDetailVoiceFileName());
	}
}
