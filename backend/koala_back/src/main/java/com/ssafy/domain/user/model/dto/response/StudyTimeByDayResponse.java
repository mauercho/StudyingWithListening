package com.ssafy.domain.user.model.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyTimeByDayResponse {

	private StudyTimeResponse mon;
	private StudyTimeResponse tue;
	private StudyTimeResponse wed;
	private StudyTimeResponse thu;
	private StudyTimeResponse fri;
	private StudyTimeResponse sat;
	private StudyTimeResponse sun;

	public static StudyTimeByDayResponse toDto(List<StudyTimeResponse> studyTimes) {
		return StudyTimeByDayResponse.builder()
			.mon(studyTimes.get(0))
			.tue(studyTimes.get(1))
			.wed(studyTimes.get(2))
			.thu(studyTimes.get(3))
			.fri(studyTimes.get(4))
			.sat(studyTimes.get(5))
			.sun(studyTimes.get(6))
			.build();
	}
}
