package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.global.util.FileUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SentenceResponse {

	private final Long id;

	private final String content;

	private final Integer order;

	private final boolean openStatus;

	private final String voiceUrl;

	@Builder
	SentenceResponse(Sentence sentence) {

		this.id = sentence.getId();
		this.content = sentence.getSentenceContentNormal();
		this.order = sentence.getOrder();
		this.openStatus = sentence.getOpenStatus();
		this.voiceUrl = FileUtil.getSentenceVoiceFileUrl(sentence.getNormalVoiceFileName());
	}
}
