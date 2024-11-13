package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {

	private String title; // 공부와 스키마의 관계

	private List<QuestionAnswerResponse> questionAnswerResponseList;

	public static QuestionResponse of(String title, List<QuestionAnswerResponse> questionAnswerResponseList) {

		return QuestionResponse.builder()
			.title(title)
			.questionAnswerResponseList(questionAnswerResponseList)
			.build();
	}

}