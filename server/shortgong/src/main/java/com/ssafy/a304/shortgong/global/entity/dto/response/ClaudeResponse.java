package com.ssafy.a304.shortgong.global.entity.dto.response;

import java.util.List;

import com.ssafy.a304.shortgong.global.entity.ClaudeMessage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaudeResponse {

	private String id;

	private List<ClaudeMessage> content;

}
