package com.ssafy.domain.translation.model.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.domain.ai_talk.model.dto.Message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GPTRequest {

	private final String model = "gpt-4o-mini";
	private final List<Message> messages;

	public GPTRequest(String language, String sentence) {
		Message message = new Message("user", "\"" + sentence + "\"를 " + language +"로 번역해줘. 번역 결과만 말해줘");
		messages = new ArrayList<>();
		messages.add(message);
	}

}
