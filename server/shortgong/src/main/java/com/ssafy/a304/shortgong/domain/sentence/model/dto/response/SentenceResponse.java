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

	private final String question;
	private final String normalAnswer;
	private final String detailAnswer;
	private final String simpleAnswer;

	private final String questionVoiceUrl;
	private final String normalAnswerVoiceUrl;
	private final String simpleAnswerVoiceUrl;
	private final String detailAnswerVoiceUrl;

	@Builder
	SentenceResponse(Sentence sentence) {

		this.id = sentence.getId();
		this.sentenceTitleId = sentence.getSentenceTitle().getId();
		this.sentencePoint = sentence.getPoint();
		this.order = sentence.getOrder();
		this.openStatus = sentence.getOpenStatus();

		this.question = sentence.getQuestion();
		this.normalAnswer = sentence.getSentenceContentNormal();
		this.detailAnswer = sentence.getSentenceContentDetail();
		this.simpleAnswer = sentence.getSentenceContentSimple();

		this.questionVoiceUrl = S3FileUtil.getPreSignedUrl(sentence.getQuestionFileName());
		this.normalAnswerVoiceUrl = S3FileUtil.getPreSignedUrl(sentence.getNormalVoiceFileName());
		this.simpleAnswerVoiceUrl = S3FileUtil.getPreSignedUrl(sentence.getSimpleVoiceFileName());
		this.detailAnswerVoiceUrl = S3FileUtil.getPreSignedUrl(sentence.getDetailVoiceFileName());
	}
}
