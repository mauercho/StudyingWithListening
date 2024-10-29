package com.ssafy.domain.lecture.model.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.lecture.model.entity.LectureNote;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureNoteResponse {

	@JsonProperty("note_id")
	private Long noteId;

	@JsonProperty("note_title")
	private String noteTitle;

	@JsonProperty("note_content")
	private String noteContent;

	@JsonProperty("note_created_at")
	private LocalDateTime noteCreatedAt;

	public static LectureNoteResponse toDto(LectureNote lectureNote) {
		return LectureNoteResponse.builder()
			.noteId(lectureNote.getNoteId())
			.noteTitle(lectureNote.getNoteTitle())
			.noteContent(lectureNote.getNoteContent())
			.noteCreatedAt(lectureNote.getNoteCreatedAt())
			.build();
	}
}
