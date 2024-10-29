package com.ssafy.domain.board.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.domain.user.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "board")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "board_title", nullable = false)
	private String title;

	@Column(name = "board_content", nullable = false)
	private String content;

	@Builder.Default
	@Column(name = "board_created_at")
	private LocalDateTime boardCreatedAt = LocalDateTime.now();

	@Column(name = "board_modified_at")
	private LocalDateTime boardModifiedAt;

	@Builder.Default
	@Column(name = "hit")
	private Integer hit = 0;

	@Builder.Default
	@Column(name = "comment_num")
	private Integer commentNum = 0;

	@OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<BoardComment> boardComments = new ArrayList<>();

	@OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<BoardImage> boardImages = new ArrayList<>();

	public void increaseHit() {
		this.hit++;
	}

	public void increaseCommentNum() {
		this.commentNum++;
	}

	public void decreaseCommentNum() {
		this.commentNum--;
	}
}
