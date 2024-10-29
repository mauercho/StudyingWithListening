package com.ssafy.domain.lecture.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureChatRequest {
	private String sender;

	private String message;
}
