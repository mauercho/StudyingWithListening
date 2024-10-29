package com.ssafy.domain.ai_talk.model.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.domain.ai_talk.model.dto.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPTRequest {

	private final String model = "gpt-4o-mini";
	private List<Message> messages;

	public GPTRequest(List<Message> chatHistory) {
		messages = new ArrayList<>(chatHistory);
	}

	public void addMessage(String message) {
		this.messages.add(new Message("user", message));
	}
}
