package com.ssafy.domain.user.service;

import com.ssafy.domain.user.model.entity.User;

public interface AiTalkLogService {

	void initAiTalkLog(User user);

	void createStartTimeLog(Long userId);

	void createEndTimeLog(Long userId);

	Integer calculateTalkTime(Long userId);

}
