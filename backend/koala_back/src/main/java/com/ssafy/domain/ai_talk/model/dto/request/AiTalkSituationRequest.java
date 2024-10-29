package com.ssafy.domain.ai_talk.model.dto.request;

import com.ssafy.domain.ai_talk.model.entity.AiTalkSituation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiTalkSituationRequest {
	private String place;
	private String aiRole;
	private String userRole;

	public static AiTalkSituationRequest toDto(AiTalkSituation aiTalkSituation) {
		return AiTalkSituationRequest.builder()
			.place(aiTalkSituation.getSituationPlace())
			.aiRole(aiTalkSituation.getAiRole())
			.userRole(aiTalkSituation.getUserRole())
			.build();
	}
}
