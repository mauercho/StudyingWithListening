package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentencesCreateResponse {

	List<SentenceCreateResponse> sentences;

	public static SentencesCreateResponse of(List<Sentence> sentences) {

		return SentencesCreateResponse.builder()
			.sentences(sentences.stream()
				.map(SentenceCreateResponse::from)
				.toList())
			.build();
	}

}
