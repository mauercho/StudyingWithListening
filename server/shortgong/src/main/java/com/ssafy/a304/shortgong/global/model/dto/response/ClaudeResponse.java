package com.ssafy.a304.shortgong.global.model.dto.response;

import java.util.List;

import com.ssafy.a304.shortgong.global.model.dto.Usage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaudeResponse {

	private String id;

	private String role;

	private List<ClaudeResponseMessage> content;

	private Usage usage;
}
