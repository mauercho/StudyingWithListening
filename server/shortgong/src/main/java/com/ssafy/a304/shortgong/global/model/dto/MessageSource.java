package com.ssafy.a304.shortgong.global.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageSource {

	private String type;

	@JsonProperty("media_type")
	private String mediaType;

	private String data;

}
