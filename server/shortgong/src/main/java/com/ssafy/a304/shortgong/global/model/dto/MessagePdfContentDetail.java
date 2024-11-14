package com.ssafy.a304.shortgong.global.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessagePdfContentDetail implements MessageContentInterface {

	private String type;

	private MessageSource source;

	@JsonProperty("cache_control")
	private CacheControl cacheControl;

}
