package com.ssafy.domain.user.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.user.model.entity.Ranking;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankingResponse {

	@JsonProperty("user_id")
	private Long userId;

	private String nickname;
	private Integer ranking;

	@Builder
	public static RankingResponse toDto(Ranking ranking) {
		return RankingResponse.builder()
			.userId(ranking.getUserId())
			.nickname(ranking.getUser().getNickname())
			.ranking(ranking.getRanking())
			.build();
	}

}
