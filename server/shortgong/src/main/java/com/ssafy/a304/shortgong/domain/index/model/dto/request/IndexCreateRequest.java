package com.ssafy.a304.shortgong.domain.index.model.dto.request;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IndexCreateRequest {

	private Boolean titleLevel;

	private String indexTitle;

	public static Index toEntity(Summary summary, Sentence sentence, IndexCreateRequest request) {

		return Index.builder()
			.summary(summary)
			.sentence(sentence)
			.titleLevel(request.getTitleLevel())
			.indexTitle(request.getIndexTitle())
			.build();
	}
}
