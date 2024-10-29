package com.ssafy.domain.board.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.board.model.entity.BoardComment;
import com.ssafy.domain.user.model.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardCommentCreateRequest {

	@NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
	@JsonProperty("comment_content")
	private String commentContent;

	public BoardComment toEntity(User user, Board board) {
		return BoardComment.builder()
			.commentContent(commentContent)
			.user(user)
			.board(board)
			.build();
	}

}
