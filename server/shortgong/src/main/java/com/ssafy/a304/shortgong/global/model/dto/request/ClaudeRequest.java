package com.ssafy.a304.shortgong.global.model.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.a304.shortgong.global.model.dto.ClaudeMessage;
import com.ssafy.a304.shortgong.global.model.dto.SystemContent;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaudeRequest {

	private String model;

	@JsonProperty("max_tokens")
	private Integer maxTokens;

	private List<ClaudeMessage> messages;

}
