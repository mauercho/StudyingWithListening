package com.ssafy.domain.board.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.user.model.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardCreateRequest {

	@NotBlank(message = "게시글 제목은 필수 입력 값입니다.")
	@JsonProperty("board_title")
	private String title;

	@NotBlank(message = "게시글 내용은 필수 입력 값입니다.")
	@JsonProperty("board_content")
	private String content;

	@Setter
	@JsonProperty("board_img_url")
	private List<MultipartFile> boardImages;

	public Board toEntity(User user) {
		return Board.builder()
			.title(title)
			.content(content)
			.user(user)
			.build();
	}
}
