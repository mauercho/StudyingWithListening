package com.ssafy.a304.shortgong.global.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SystemContent {

	private String type;

	private String text;

	private CacheControl cacheControl;

}
