package com.ssafy.domain.translation.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.ai_talk.model.dto.Message;

import lombok.Getter;

@Getter
public class GPTResponse {

	private List<Choice> choices;

	public static class Choice {
		private Message message;

		@JsonCreator
		public Choice(@JsonProperty("message") Message message) {
			this.message = message;
		}

		public Message getMessage() {
			return message;
		}

		public void setMessage(Message message) {
			this.message = message;
		}
	}

}
