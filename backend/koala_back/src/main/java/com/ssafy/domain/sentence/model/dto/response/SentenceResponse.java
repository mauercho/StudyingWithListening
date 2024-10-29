package com.ssafy.domain.sentence.model.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.sentence.model.entity.Sentence;
import com.ssafy.domain.user.model.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentenceResponse {

	@JsonProperty("sentence_id")
	private Long sentenceId;

	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("sentence_text")
	private String sentenceText;

	@JsonProperty("topic_category")
	private String topicCategory;

	@JsonProperty("sentence_length")
	private Integer sentenceLength;

	@JsonProperty("sentence_created_at")
	private LocalDateTime sentenceCreatedAt;

	public static SentenceResponse toDto(Sentence sentence) {
		return SentenceResponse.builder()
			.sentenceId(sentence.getSentenceId())
			.userId(sentence.getUser().getUserId())
			.sentenceText(sentence.getSentenceText())
			.topicCategory(sentence.getTopicCategory())
			.sentenceLength(sentence.getSentenceLength())
			.sentenceCreatedAt(sentence.getSentenceCreatedAt())
			.build();
	}

	public static Sentence toEntity(SentenceResponse sentenceResponse, User user) {
		return Sentence.builder()
			.sentenceId(sentenceResponse.getSentenceId())
			.user(user)
			.sentenceText(sentenceResponse.getSentenceText())
			.topicCategory(sentenceResponse.getTopicCategory())
			.sentenceLength(sentenceResponse.getSentenceLength())
			.sentenceCreatedAt(sentenceResponse.getSentenceCreatedAt())
			.build();
	}

}
