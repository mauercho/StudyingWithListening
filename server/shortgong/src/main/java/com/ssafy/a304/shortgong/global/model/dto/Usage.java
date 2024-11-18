package com.ssafy.a304.shortgong.global.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usage {

	@JsonProperty("cache_creation_input_tokens")
	private int cacheCreationInputTokens;

	@JsonProperty("cache_read_input_tokens")
	private int cacheReadInputTokens;

	@JsonProperty("input_tokens")
	private int inputTokens;
}