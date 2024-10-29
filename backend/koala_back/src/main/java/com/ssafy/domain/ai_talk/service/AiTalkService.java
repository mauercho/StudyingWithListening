package com.ssafy.domain.ai_talk.service;

import java.util.List;

import com.ssafy.domain.ai_talk.model.dto.request.AiTalkRequest;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkFinishResponse;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkResponse;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkSituationResponse;

public interface AiTalkService {
	List<AiTalkSituationResponse> getAllAiTalkSituations();

	List<AiTalkSituationResponse> getAiTalkSituationByTopic(String topic);

	AiTalkResponse setSituation(Long situationId);

	AiTalkResponse getAiResponse(AiTalkRequest aiTalkRequest);

	AiTalkFinishResponse finishAiResponse();
}
