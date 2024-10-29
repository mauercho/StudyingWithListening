package com.ssafy.domain.ai_talk.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

	private String role;
	private String content;

	@JsonCreator
	public Message(@JsonProperty("role") String role, @JsonProperty("content") String content) {
		this.role = role;
		this.content = content;
	}
}
