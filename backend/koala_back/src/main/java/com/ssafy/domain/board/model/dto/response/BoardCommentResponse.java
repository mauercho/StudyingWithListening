package com.ssafy.domain.board.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.board.model.entity.BoardComment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardCommentResponse {

	@JsonProperty("comment_id")
	private Long commentId;

	private String content;
	private String nickname;

	@JsonProperty("created_at")
	private String createdAt;

	public static BoardCommentResponse toDto(BoardComment boardComment) {
		return BoardCommentResponse.builder()
			.commentId(boardComment.getCommentId())
			.content(boardComment.getCommentContent())
			.nickname(boardComment.getUser().getNickname())
			.createdAt(boardComment.getCommentCreatedAt().toString())
			.build();
	}
}
