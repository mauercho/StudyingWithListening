package com.ssafy.domain.ai_talk.model.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.domain.ai_talk.model.dto.Message;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class GPTSituationRequest {
	private final String model = "gpt-4o-mini";
	private List<Message> messages;

	public GPTSituationRequest(AiTalkSituationRequest aiTalkSituation) {
		messages = new ArrayList<>();
		this.messages.add(new Message("system", "너는 한국어를 연습하고 있는 사람을 돕는 회화 도우미야. 쉬운 한국어로 한국어 초보자를 상황극을 통해 도울 필요가 있어."));
		this.messages.add(new Message("system",
			"지금부터 너는 " + aiTalkSituation.getPlace() + " 에서 대화를 할 거야." + " 너는 " + aiTalkSituation.getAiRole()
				+ " 역할이야." + " 상대는 " + aiTalkSituation.getUserRole() + " 역할이야."));
		this.messages.add(new Message("system", "해당 역할에 맞게 너가 상황극을 바로 시작해. 응답은 간결하게 역할에 맞는 대답만 하면 돼."));
		log.info(messages.toString());
	}
}
