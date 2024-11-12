package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponse {

	String title; // 공부와 스키마의 관계

	List<QuestionAnswerResponse> questionAnswerResponseList;

}