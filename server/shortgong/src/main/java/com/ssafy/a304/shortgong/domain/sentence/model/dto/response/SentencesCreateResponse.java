package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentencesCreateResponse {

	List<SentenceCreateResponse> sentences;

	public static SentencesCreateResponse of(List<SentenceCreateResponse> sentences) {

		return SentencesCreateResponse.builder()
			.sentences(sentences)
			.build();
	}

}
