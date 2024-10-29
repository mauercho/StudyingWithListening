package com.ssafy.domain.board.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.domain.board.model.dto.request.BoardCreateRequest;
import com.ssafy.domain.board.model.dto.response.BoardDetailResponse;
import com.ssafy.domain.board.model.dto.response.BoardResponse;

public interface BoardService {

	BoardDetailResponse writeBoard(BoardCreateRequest boardCreateRequest) throws IOException;

	Page<BoardResponse> getBoards(Pageable pageable);

	BoardDetailResponse getBoard(Long boardId, Pageable pageable);

	Page<BoardResponse> getBoardsSortedByHit(Pageable pageable);

	Page<BoardResponse> getBoardsByKeyword(String keyword, Pageable pageable);

	Page<BoardResponse> getBoardsByUser(Pageable pageable);

	void deleteBoard(Long boardId);

	void increaseCommentNum(Long boardId);

	void decreaseCommentNum(Long boardId);

	void increaseHit(Long boardId);

}
