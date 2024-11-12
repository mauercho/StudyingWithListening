package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionAnswerResponse {

	String point; // 스키마를 통한 지식 습득

	String question; // 스키마란 무엇이며, 새로운 지식을 습득하는 과정에서 어떤 역할을 하나요?

	String answer; // 새로운 지식은 기존의 지식을 통해 습득됩니다. 사람은 자신이 이미 가지고 있는 지식을 기반으로 새로운 지식을 이해하고 해석하게 됩니다.

}
