package com.ssafy.domain.review.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.review.model.entity.ReviewSentence;
import com.ssafy.domain.sentence.model.entity.Sentence;
import com.ssafy.domain.user.model.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveRequest {

	@NotNull(message = "문장 id는 필수 입력 값입니다.")
	@JsonProperty("sentence_id")
	private Long sentenceId;

	public ReviewSentence toReviewSentenceEntity(Sentence sentence, User user) {
		return ReviewSentence.builder().sentence(sentence).user(user).build();
	}

}
