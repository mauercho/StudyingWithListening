package com.ssafy.domain.board.model.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.board.model.entity.Board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDetailResponse {

	@JsonProperty("board_id")
	private Long boardId;

	private String title;
	private String content;
	private String nickname;

	@JsonProperty("comment_num")
	private int commentNum;

	@JsonProperty("view_count")
	private int viewCount;

	@JsonProperty("created_at")
	private String createdAt;

	private Page<BoardCommentResponse> comments;

	@JsonProperty("board_images")
	private List<String> boardImages;

	public static BoardDetailResponse toDto(Board board, List<String> boardImages,
		Page<BoardCommentResponse> comments) {
		return BoardDetailResponse.builder()
			.boardId(board.getId())
			.title(board.getTitle())
			.content(board.getContent())
			.nickname(board.getUser().getNickname())
			.commentNum(board.getCommentNum())
			.viewCount(board.getHit())
			.createdAt(board.getBoardCreatedAt().toString())
			.comments(comments)
			.boardImages(boardImages)
			.build();
	}

}
