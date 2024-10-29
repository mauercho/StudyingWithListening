package com.ssafy.domain.user.model.dto.response;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalStudyTimeResponse {

	@JsonProperty("last_week")
	private StudyTimeByDayResponse lastWeek;

	@JsonProperty("this_week")
	private StudyTimeByDayResponse thisWeek;

	private StudyTimeResponse total;
	private String today;

	public static TotalStudyTimeResponse toDto(StudyTimeByDayResponse lastWeek, StudyTimeByDayResponse thisWeek,
		StudyTimeResponse total, DayOfWeek today) {
		return TotalStudyTimeResponse.builder()
			.lastWeek(lastWeek)
			.thisWeek(thisWeek)
			.total(total)
			.today(today.toString().substring(0, 3).toLowerCase())
			.build();
	}
}
