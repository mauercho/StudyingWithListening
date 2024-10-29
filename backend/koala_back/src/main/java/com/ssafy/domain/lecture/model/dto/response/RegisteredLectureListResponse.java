package com.ssafy.domain.lecture.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisteredLectureListResponse {

	@JsonProperty("lecture_list")
	private List<RegisteredLectureResponse> lectureList;

	public static RegisteredLectureListResponse toDto(List<RegisteredLectureResponse> lectureList) {
		return RegisteredLectureListResponse.builder()
			.lectureList(lectureList)
			.build();
	}

}
