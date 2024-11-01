package com.ssafy.a304.shortgong.domain.summary.model.dto.response;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SummaryWithOutWriterResponse {

	private Long id;

	private UploadContent uploadContent;

	private String summaryTitle;

	private String folderName;

	public static SummaryWithOutWriterResponse from(Summary summary, UploadContent uploadContent) {

		return SummaryWithOutWriterResponse.builder()
			.id(summary.getId())
			.uploadContent(uploadContent)
			.summaryTitle(summary.getSummaryTitle())
			.folderName(summary.getFolderName())
			.build();
	}

}
