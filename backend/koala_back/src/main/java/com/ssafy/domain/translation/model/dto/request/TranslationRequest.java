package com.ssafy.domain.translation.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TranslationRequest {
	@NotNull(message = "번역할 언어 선택은 필수입니다.")
	String language;

	@NotNull(message = "번역을 원하는 문장은 필수 입력 값입니다.")
	@JsonProperty("origin_sentence")
	String originSentence;
}
