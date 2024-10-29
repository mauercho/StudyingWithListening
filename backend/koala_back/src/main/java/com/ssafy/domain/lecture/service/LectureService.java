package com.ssafy.domain.lecture.service;

import java.util.List;

import com.ssafy.domain.lecture.model.dto.request.LectureNoteRequest;
import com.ssafy.domain.lecture.model.dto.response.LectureNoteResponse;
import com.ssafy.domain.lecture.model.dto.response.LectureResponse;
import com.ssafy.domain.lecture.model.dto.response.RegisteredLectureResponse;

public interface LectureService {

	List<LectureResponse> getAllLecture();

	void registerLecture(Long lectureId);

	List<RegisteredLectureResponse> getRegisteredLecture();

	LectureNoteResponse writeLectureNote(LectureNoteRequest lectureNoteRequest);

	List<LectureNoteResponse> getLectureNote(Long lectureId);

	boolean deleteLectureNote(Long lectureId);

	LectureResponse setSessionId(Long lectureId, String sessionId);

	String getSessionId(Long lectureId);

}
