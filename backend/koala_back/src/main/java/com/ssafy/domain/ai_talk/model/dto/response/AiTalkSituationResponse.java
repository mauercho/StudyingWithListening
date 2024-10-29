package com.ssafy.domain.ai_talk.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.ai_talk.model.entity.AiTalkSituation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiTalkSituationResponse {

	@JsonProperty("situation_id")
	private Long situationId;

	@JsonProperty("topic_category")
	private String topicCategory;

	@JsonProperty("situation_title")
	private String situationTitle;

	@JsonProperty("situation_detail")
	private String situationDetail;

	@JsonProperty("situation_place")
	private String situationPlace;

	@JsonProperty("situation_img_url")
	private String situationImgUrl;

	public static AiTalkSituationResponse toDto(AiTalkSituation aiTalkSituation) {
		return AiTalkSituationResponse.builder()
			.situationId(aiTalkSituation.getSituationId())
			.topicCategory(aiTalkSituation.getTopicCategory())
			.situationPlace(aiTalkSituation.getSituationPlace())
			.situationTitle(aiTalkSituation.getSituationTitle())
			.situationDetail(aiTalkSituation.getSituationDetail())
			.situationImgUrl(aiTalkSituation.getSituationImgUrl())
			.build();
	}

}
