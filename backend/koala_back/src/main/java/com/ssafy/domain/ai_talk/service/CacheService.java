package com.ssafy.domain.ai_talk.service;

import java.util.List;

import com.ssafy.domain.ai_talk.model.dto.Message;
import com.ssafy.domain.ai_talk.model.dto.request.AiTalkSituationRequest;

public interface CacheService {
	boolean isEmpty(String loginId);

	void initCacheMemory(String loginId, AiTalkSituationRequest aiTalkSituation);

	List<Message> getChatHistory(String loginId);

	void addChatHistory(String loginId, Message message);

	void clearChatHistory(String loginId);
}
