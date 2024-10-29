package com.ssafy.domain.user.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankingWithMyRankResponse {

	private List<RankingResponse> rankings;

	@JsonProperty("my_rank")
	private Integer myRank;

	@Builder
	public static RankingWithMyRankResponse toDto(List<RankingResponse> rankings, Integer myRank) {
		return RankingWithMyRankResponse.builder()
			.rankings(rankings)
			.myRank(myRank)
			.build();
	}

}
