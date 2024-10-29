package com.ssafy.domain.user.service;

import com.ssafy.domain.user.model.dto.response.TotalStudyTimeResponse;

public interface StudyTimeService {

	TotalStudyTimeResponse getStudyTime();

	void initStudyTime(Long userId);

	void increaseDictationCount(Integer DictationCount);

	void increaseLectureCount();

	Integer increaseAiTalkMinutes();

	void increaseTotalStudyTime(Long userId, Integer timeCalType, Integer studyTime);

}
