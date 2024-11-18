package com.ssafy.a304.shortgong.domain.alert;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionCreatedMessageResponse {

	private final Long summaryId;
	private final List<String> questions;

	@Builder
	public QuestionCreatedMessageResponse(Long summaryId, List<String> questions) {

		this.summaryId = summaryId;
		this.questions = questions;

	}

}
