package com.ssafy.domain.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.board.model.dto.request.BoardCreateRequest;
import com.ssafy.domain.board.model.dto.response.BoardCommentResponse;
import com.ssafy.domain.board.model.dto.response.BoardDetailResponse;
import com.ssafy.domain.board.model.dto.response.BoardResponse;
import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.board.model.entity.BoardImage;
import com.ssafy.domain.board.repository.BoardCommentRepository;
import com.ssafy.domain.board.repository.BoardImageRepository;
import com.ssafy.domain.board.repository.BoardRepository;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

	private final UserInfoProvider userInfoProvider;
	private final BoardRepository boardRepository;
	private final BoardCommentRepository boardCommentRepository;
	private final BoardImageRepository boardImageRepository;
	private final BoardImageService boardImageService;

	@Override
	@Transactional
	public BoardDetailResponse writeBoard(BoardCreateRequest boardCreateRequest) throws IOException {
		User user = userInfoProvider.getCurrentUser();
		Board board = boardRepository.save(boardCreateRequest.toEntity(user));

		Pageable pageable = Pageable.ofSize(10).withPage(0);
		Page<BoardCommentResponse> comments = boardCommentRepository.findByBoardId(board.getId(), pageable)
			.map(BoardCommentResponse::toDto);

		List<String> imgUrlList = boardImageService.saveBoardImages(board.getId(), boardCreateRequest.getBoardImages());

		return BoardDetailResponse.toDto(board, imgUrlList, comments);
	}

	@Override
	public Page<BoardResponse> getBoards(Pageable pageable) {
		Page<Board> boards = boardRepository.findAllByOrderByBoardCreatedAtDesc(pageable);
		return boards.map(BoardResponse::toDto);
	}

	@Override
	public BoardDetailResponse getBoard(Long boardId, Pageable pageable) {
		Page<BoardCommentResponse> comments = boardCommentRepository.findByBoardId(boardId, pageable)
			.map(BoardCommentResponse::toDto);

		List<BoardImage> boardImages = boardImageRepository.findAllByBoardId(boardId);
		List<String> imgUrlList = boardImages.stream()
			.map(BoardImage::getBoardImgUrl)
			.toList();

		return BoardDetailResponse.toDto(boardRepository.findById(boardId).orElseThrow(), imgUrlList, comments);
	}

	@Override
	public Page<BoardResponse> getBoardsSortedByHit(Pageable pageable) {
		Page<Board> boards = boardRepository.findAllByOrderByHitDesc(pageable);
		return boards.map(BoardResponse::toDto);
	}

	@Override
	public Page<BoardResponse> getBoardsByKeyword(String keyword, Pageable pageable) {
		Page<Board> boards = boardRepository.findAllByTitleContaining(keyword, pageable);
		return boards.map(BoardResponse::toDto);
	}

	@Override
	public Page<BoardResponse> getBoardsByUser(Pageable pageable) {
		User user = userInfoProvider.getCurrentUser();
		Page<Board> boards = boardRepository.findAllByUser(user, pageable);
		return boards.map(BoardResponse::toDto);
	}

	@Override
	@Transactional
	public void deleteBoard(Long boardId) {
		boardRepository.deleteById(boardRepository.findById(boardId).orElseThrow().getId());
	}

	@Override
	@Transactional
	public void increaseCommentNum(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow();
		board.increaseCommentNum();
		boardRepository.save(board);
	}

	@Override
	@Transactional
	public void decreaseCommentNum(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow();
		board.decreaseCommentNum();
		boardRepository.save(board);
	}

	@Override
	@Transactional
	public void increaseHit(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow();
		board.increaseHit();
		boardRepository.save(board);
	}
}
