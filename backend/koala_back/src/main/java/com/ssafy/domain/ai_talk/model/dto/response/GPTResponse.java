package com.ssafy.domain.ai_talk.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.ai_talk.model.dto.Message;

public class GPTResponse {

	private List<Choice> choices;

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

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
