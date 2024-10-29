package com.ssafy.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.board.model.dto.request.BoardCommentCreateRequest;
import com.ssafy.domain.board.model.dto.response.BoardCommentResponse;
import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.board.repository.BoardCommentRepository;
import com.ssafy.domain.board.repository.BoardRepository;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentServiceImpl implements BoardCommentService {

	private final UserInfoProvider userInfoProvider;
	private final BoardRepository boardRepository;
	private final BoardCommentRepository boardCommentRepository;
	private final BoardService boardService;

	@Override
	@Transactional
	public BoardCommentResponse leaveComment(Long boardId, BoardCommentCreateRequest boardCommentCreateRequest) {
		Board currentBoard = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		boardService.increaseCommentNum(boardId);
		return BoardCommentResponse.toDto(boardCommentRepository.save(
			boardCommentCreateRequest.toEntity(userInfoProvider.getCurrentUser(), currentBoard)));
	}

	@Override
	@Transactional
	public void deleteComment(Long boardId, Long commentId) {
		boardCommentRepository.delete(boardCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다.")));
		boardService.decreaseCommentNum(boardId);
	}
}
