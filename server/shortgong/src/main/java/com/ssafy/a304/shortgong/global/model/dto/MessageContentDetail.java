package com.ssafy.a304.shortgong.global.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageContentDetail implements MessageContentInterface {

	private String type;

	private String text;

}
