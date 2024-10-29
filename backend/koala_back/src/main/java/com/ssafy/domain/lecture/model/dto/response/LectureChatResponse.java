package com.ssafy.domain.lecture.model.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureChatResponse {
	private String sender;

	private String message;

	@JsonProperty("sending_time")
	private LocalDateTime sendingTime;
}
