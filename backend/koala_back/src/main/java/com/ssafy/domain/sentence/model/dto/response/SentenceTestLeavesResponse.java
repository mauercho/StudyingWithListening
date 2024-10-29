package com.ssafy.domain.sentence.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentenceTestLeavesResponse {

	@JsonProperty("sentence_test_responses")
	private List<SentenceTestResponse> sentenceTestResponses;

	private int leaves;

	public static SentenceTestLeavesResponse toDto(List<SentenceTestResponse> sentenceTestResponses, int leaves) {
		return SentenceTestLeavesResponse.builder()
			.sentenceTestResponses(sentenceTestResponses)
			.leaves(leaves)
			.build();
	}
}
