package com.ssafy.domain.lecture.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.lecture.model.entity.RegisteredLecture;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisteredLectureResponse {

	@JsonProperty("lecture_id")
	private Long lectureId;

	@JsonProperty("teacher_name")
	private String teacherName;

	@JsonProperty("lecture_title")
	private String lectureTitle;

	@JsonProperty("lecture_img_url")
	private String lectureImgUrl;

	@JsonProperty("lecture_note_count")
	private Long lectureNoteCount;

	public static RegisteredLectureResponse toDto(RegisteredLecture lecture, Long lectureNoteCount) {
		return RegisteredLectureResponse.builder()
			.lectureId(lecture.getLecture().getLectureId())
			.teacherName(lecture.getLecture().getTeacher().getName())
			.lectureTitle(lecture.getLecture().getLectureTitle())
			.lectureImgUrl(lecture.getLecture().getLectureImgUrl())
			.lectureNoteCount(lectureNoteCount)
			.build();
	}
}
