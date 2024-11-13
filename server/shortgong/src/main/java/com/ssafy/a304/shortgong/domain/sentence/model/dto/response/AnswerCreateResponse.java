package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerCreateResponse {

	private String SimpleAnswer;

	private String NormalAnswer;

	private String DetailAnswer;

	public static AnswerCreateResponse of(String SimpleAnswer, String NormalAnswer, String DetailAnswer) {

		return AnswerCreateResponse.builder()
			.SimpleAnswer(SimpleAnswer)
			.NormalAnswer(NormalAnswer)
			.DetailAnswer(DetailAnswer)
			.build();
	}
}
