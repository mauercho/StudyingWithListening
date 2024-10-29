package com.ssafy.domain.lecture.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.lecture.model.dto.request.LectureNoteRequest;
import com.ssafy.domain.lecture.model.dto.response.LectureNoteResponse;
import com.ssafy.domain.lecture.model.dto.response.LectureResponse;
import com.ssafy.domain.lecture.model.dto.response.RegisteredLectureListResponse;
import com.ssafy.domain.lecture.service.LectureService;
import com.ssafy.domain.sentence.service.SentenceService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

	private final LectureService lectureService;
	private final SentenceService sentenceService;

	@Operation(summary = "모든 강의 조회")
	@GetMapping("/all")
	public ResponseEntity<?> getAllLecture() {
		List<LectureResponse> lectures = lectureService.getAllLecture();
		if (lectures.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(lectures);
		}
	}

	@Operation(summary = "수강한 강의 조회")
	@GetMapping
	public ResponseEntity<?> getAllRegisteredLecture() {
		RegisteredLectureListResponse registeredLectureListResponse = RegisteredLectureListResponse.toDto(
			lectureService.getRegisteredLecture());
		if (registeredLectureListResponse.getLectureList().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(registeredLectureListResponse);
		}
	}

	@Operation(summary = "강의노트 등록")
	@PostMapping("/note")
	public ResponseEntity<?> createLectureNote(@Valid @RequestBody LectureNoteRequest lectureNoteRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(lectureService.writeLectureNote(lectureNoteRequest));
	}

	@Operation(summary = "강의 {lectureId}에 등록된 모든 강의노트 조회")
	@GetMapping("/{lecture_id}/note")
	public ResponseEntity<?> getLectureNote(@PathVariable("lecture_id") Long lectureId) {
		List<LectureNoteResponse> lectureNotes = lectureService.getLectureNote(lectureId);
		if (lectureNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(lectureNotes);
		}
	}

	@Operation(summary = "강의노트 {note_id} 삭제")
	@DeleteMapping("/note/{note_id}")
	public ResponseEntity<?> deleteLectureNote(@PathVariable("note_id") Long noteId) {
		if (lectureService.deleteLectureNote(noteId))
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully deleted lecture note!"));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Not found with id: " + noteId));
	}

	@Operation(summary = "강의 {lecture_id}에서 제공하는 문장 조회")
	@GetMapping("/{lecture_id}/sentences")
	public ResponseEntity<?> getLectureSentences(@PathVariable("lecture_id") Long lectureId) {
		return ResponseEntity.status(HttpStatus.OK).body(sentenceService.getLectureSentences(lectureId));
	}
}
