package com.ssafy.domain.sentence.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentenceTestResponse {

	@JsonProperty("origin_text")
	private String originText;

	@JsonProperty("user_text")
	private String userText;

	@JsonProperty("result_tag")
	private String resultTag;

	private boolean correct;

	private boolean registered;

	public static SentenceTestResponse toDto(String originText, String userText, String resultTag, boolean correct, boolean registered) {
		return SentenceTestResponse.builder()
			.originText(originText)
			.userText(userText)
			.resultTag(resultTag)
			.correct(correct)
			.registered(registered)
			.build();
	}

}
