package com.ssafy.domain.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.domain.board.model.entity.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

	List<BoardImage> findAllByBoardId(Long boardId);

}
