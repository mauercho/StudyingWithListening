package com.ssafy.domain.board.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardImageRequest {

	@JsonProperty("board_id")
	private Long boardId;

	@JsonProperty("board_img_url")
	private List<MultipartFile> boardImages;

}
