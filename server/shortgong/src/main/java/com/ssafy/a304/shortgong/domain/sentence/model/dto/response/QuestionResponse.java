package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResponse {

	private String title; // 공부와 스키마의 관계

	private List<QuestionAnswerResponse> questionAnswerResponseList = new ArrayList<>();

	public void addAnswerResponse(QuestionAnswerResponse questionAnswerResponse) {

		questionAnswerResponseList.add(questionAnswerResponse);
	}

}