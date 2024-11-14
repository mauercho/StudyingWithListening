package com.ssafy.a304.shortgong.global.model.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.a304.shortgong.global.model.dto.MessageContent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaudePdfRequest {

	private String model;

	@JsonProperty("max_tokens")
	private Integer maxTokens;

	private List<MessageContent> messages;

}
