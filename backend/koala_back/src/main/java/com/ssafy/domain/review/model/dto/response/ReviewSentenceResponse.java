package com.ssafy.domain.review.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.review.model.entity.ReviewSentence;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSentenceResponse {

	@JsonProperty("review_sentence_id")
	private Long reviewSentenceId;

	@JsonProperty("sentence_id")
	private Long sentenceId;

	@JsonProperty("topic_category")
	private String topicCategory;

	@JsonProperty("sentence_text")
	private String sentenceText;

	public static ReviewSentenceResponse toDto(ReviewSentence reviewSentence) {
		return ReviewSentenceResponse.builder()
			.reviewSentenceId(reviewSentence.getReviewSentenceId())
			.sentenceId(reviewSentence.getSentence().getSentenceId())
			.topicCategory(reviewSentence.getSentence().getTopicCategory())
			.sentenceText(reviewSentence.getSentence().getSentenceText())
			.build();
	}

}
