package com.ssafy.a304.shortgong.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaudeMessage {

	private String role;

	private String content;

}
