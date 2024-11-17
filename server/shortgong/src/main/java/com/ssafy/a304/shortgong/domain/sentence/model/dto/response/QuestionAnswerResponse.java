package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerResponse {

	private String point; // 스키마를 통한 지식 습득
	private String question; // 스키마란 무엇이며, 새로운 지식을 습득하는 과정에서 어떤 역할을 하나요?
	private String answerSimple;
	private String answerNormal;

}
