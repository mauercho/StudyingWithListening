package com.ssafy.domain.ai_talk.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiTalkFinishResponse {

	private String message = "finish AI chat";

	private int leaves;

	public static AiTalkFinishResponse toDto(int leaves) {
		return AiTalkFinishResponse.builder()
			.leaves(leaves)
			.build();
	}
}
