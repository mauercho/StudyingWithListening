package com.ssafy.a304.shortgong.global.entity.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.a304.shortgong.global.entity.ClaudeMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaudeRequest {

	private String model;

	private List<ClaudeMessage> messages;

	private Double temperature;

	@JsonProperty("max_tokens")
	private Integer maxTokens;

}
