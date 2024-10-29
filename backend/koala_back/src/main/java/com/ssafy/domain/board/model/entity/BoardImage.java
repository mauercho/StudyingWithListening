package com.ssafy.domain.board.model.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "board_images")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class BoardImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_img_id")
	private Long boardImgId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@Column(name = "board_img_url")
	private String boardImgUrl; // S3 내부 이미지에 접근할 수 있는 URL

	@Column(name = "img_origin_name")
	private String imgOriginName; // 이미지 파일의 원본 이름

	@Column(name = "img_stored_name")
	private String imgStoredName; // 이미지 파일이 S3에 저장될 때 사용되는 이름

}
