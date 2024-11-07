package com.ssafy.a304.shortgong.domain.summary.model.dto.response;

import java.time.LocalDateTime;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

import lombok.Getter;

@Getter
public class SummaryOverviewResponse {

	private final Long id;

	private final String title;

	private final LocalDateTime createdAt;

	private final LocalDateTime modifiedAt;

	public SummaryOverviewResponse(Summary summary) {

		this.id = summary.getId();
		this.title = summary.getTitle();
		this.createdAt = summary.getCreatedAt();
		this.modifiedAt = summary.getModifiedAt();
	}
}
