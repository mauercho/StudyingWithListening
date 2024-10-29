package com.ssafy.domain.lecture.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.lecture.model.entity.Lecture;
import com.ssafy.domain.lecture.model.entity.LectureNote;
import com.ssafy.domain.user.model.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureNoteRequest {

	@JsonProperty("lecture_id")
	private Long lectureId;

	@JsonProperty("note_title")
	private String noteTitle;

	@JsonProperty("note_content")
	private String noteContent;

	public LectureNote toEntity(User user, Lecture lecture) {
		return LectureNote.builder()
			.user(user)
			.lecture(lecture)
			.noteTitle(noteTitle)
			.noteContent(noteContent)
			.build();
	}
}
