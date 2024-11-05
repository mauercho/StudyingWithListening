package com.ssafy.a304.shortgong.global.model.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaudeResponseMessage {

	private String text;

	private String type;

}
