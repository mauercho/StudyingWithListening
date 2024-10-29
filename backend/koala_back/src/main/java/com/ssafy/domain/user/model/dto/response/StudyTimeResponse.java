package com.ssafy.domain.user.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.user.model.entity.StudyTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyTimeResponse {

	@JsonProperty("speaking")
	private Integer talkTime;

	@JsonProperty("writing")
	private Integer sentenceNum;

	@JsonProperty("lectures")
	private Integer lectureNum;

	public static StudyTimeResponse toDto(StudyTime studyTime) {
		return StudyTimeResponse.builder()
			.talkTime(studyTime.getTalkTime())
			.sentenceNum(studyTime.getSentenceNum())
			.lectureNum(studyTime.getLectureNum())
			.build();
	}
}
