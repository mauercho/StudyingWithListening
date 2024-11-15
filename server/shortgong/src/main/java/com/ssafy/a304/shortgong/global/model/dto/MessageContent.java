package com.ssafy.a304.shortgong.global.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageContent {

	private String role;

	private List<MessageContentInterface> content;

}
