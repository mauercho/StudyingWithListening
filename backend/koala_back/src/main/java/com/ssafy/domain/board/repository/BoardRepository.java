package com.ssafy.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.user.model.entity.User;

public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findAllByOrderByBoardCreatedAtDesc(Pageable pageable);

	Page<Board> findAllByOrderByHitDesc(Pageable pageable);

	Page<Board> findAllByTitleContaining(String keyword, Pageable pageable);

	@Query("select b from Board b where b.user = :user")
	Page<Board> findAllByUser(@Param("user") User user, Pageable pageable);

}
