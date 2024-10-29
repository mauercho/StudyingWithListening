package com.ssafy.domain.board.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.board.model.entity.Board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {

	@JsonProperty("board_id")
	private Long boardId;

	private String title;
	private String content;
	private String nickname;

	@JsonProperty("comment_num")
	private int commentNum;

	private int hit;

	@JsonProperty("created_at")
	private String createdAt;

	private String thumbnail;

	public static BoardResponse toDto(Board board) {
		return BoardResponse.builder()
			.boardId(board.getId())
			.title(board.getTitle())
			.content(board.getContent())
			.nickname(board.getUser().getNickname())
			.commentNum(board.getCommentNum())
			.hit(board.getHit())
			.createdAt(board.getBoardCreatedAt().toString())
			.thumbnail(board.getBoardImages().isEmpty() ? null : board.getBoardImages().get(0).getBoardImgUrl())
			.build();
	}
}
