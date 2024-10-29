package com.ssafy.domain.lecture.service;

import com.ssafy.domain.lecture.model.dto.request.LectureChatRequest;

public interface LectureChatService {

	void sendLectureChat(Long lectureId, LectureChatRequest chat);

}
