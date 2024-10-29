package com.ssafy.domain.sentence.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.sentence.model.entity.LectureSentence;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureSentenceResponse {

	@JsonProperty("sentence_id")
	private Long sentenceId;

	@JsonProperty("sentence_text")
	private String sentenceText;

	private boolean registered;

	public static LectureSentenceResponse toDto(LectureSentence lectureSentence, boolean registered) {
		return LectureSentenceResponse.builder()
			.sentenceId(lectureSentence.getSentence().getSentenceId())
			.sentenceText(lectureSentence.getSentence().getSentenceText())
			.registered(registered)
			.build();
	}
}
