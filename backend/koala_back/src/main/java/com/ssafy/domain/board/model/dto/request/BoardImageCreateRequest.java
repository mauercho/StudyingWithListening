package com.ssafy.domain.board.model.dto.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.board.model.entity.BoardImage;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardImageCreateRequest {

	@Setter
	@JsonProperty("board_img_url")
	private String boardImgUrl;

	@JsonProperty("img_origin_name")
	private String imgOriginName;

	@JsonProperty("img_stored_name")
	private String imgStoredName;

	public BoardImageCreateRequest(String imgOriginName) {
		this.imgOriginName = imgOriginName;
		this.imgStoredName = getFileName(imgOriginName);
		this.boardImgUrl = "";
	}

	// 이미지 파일의 확장자를 추출하는 메소드
	public String extractExtension(String imgOriginName) {
		int index = imgOriginName.lastIndexOf(".");

		return imgOriginName.substring(index);
	}

	// 이미지 파일의 이름을 저장하기 위한 이름으로 변환하는 메소드
	public String getFileName(String imgOriginName) {
		return UUID.randomUUID() + "." + extractExtension(imgOriginName);
	}

	public BoardImage toEntity(Board board) {
		return BoardImage.builder()
			.boardImgUrl(boardImgUrl)
			.imgOriginName(imgOriginName)
			.imgStoredName(imgStoredName)
			.board(board)
			.build();
	}

}
