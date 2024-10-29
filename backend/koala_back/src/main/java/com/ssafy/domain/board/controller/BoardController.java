package com.ssafy.domain.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.domain.board.model.dto.request.BoardCommentCreateRequest;
import com.ssafy.domain.board.model.dto.request.BoardCreateRequest;
import com.ssafy.domain.board.model.dto.response.BoardCommentResponse;
import com.ssafy.domain.board.model.dto.response.BoardDetailResponse;
import com.ssafy.domain.board.model.validation.BoardValidation;
import com.ssafy.domain.board.service.BoardCommentService;
import com.ssafy.domain.board.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;
	private final BoardCommentService boardCommentService;

	@Operation(summary = "게시글 작성")
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createBoard(
		@Valid @RequestPart(value = "board_detail") String boardDetail,
		@RequestPart(value = "board_images", required = false) List<MultipartFile> images) throws IOException {

		// JSON 문자열을 객체로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		BoardCreateRequest boardCreateRequest = objectMapper.readValue(boardDetail, BoardCreateRequest.class);

		if (BoardValidation.validateKoreanAndNumeric(boardCreateRequest.getTitle())) {
			throw new IllegalArgumentException(
				"The post title can only contain Korean letters, numbers, and special characters.");
		}
		if (BoardValidation.validateKoreanAndNumeric(boardCreateRequest.getContent())) {
			throw new IllegalArgumentException(
				"Post content can only contain Korean letters, numbers, and special characters.");
		}
		// 추가: files 리스트를 boardCreateRequest에 설정
		boardCreateRequest.setBoardImages(images != null ? images : new ArrayList<>());

		BoardDetailResponse boardCreateResponse = boardService.writeBoard(boardCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(boardCreateResponse);
	}

	@Operation(summary = "게시글 목록 조회", description = "query parameter로 page, size 페이징 처리")
	@GetMapping
	public ResponseEntity<?> getBoards(Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoards(pageable));
	}

	@Operation(summary = "게시글 상세 조회", description = "게시글 조회 시 조회수 증가")
	@GetMapping("/{board_id}/comments")
	public ResponseEntity<?> getBoard(@PathVariable("board_id") Long boardId, Pageable pageable) {
		BoardDetailResponse boardDetailResponse = boardService.getBoard(boardId, pageable);
		boardService.increaseHit(boardId);
		return ResponseEntity.status(HttpStatus.OK).body(boardDetailResponse);
	}

	@Operation(summary = "게시글 조회수 순 정렬 목록 조회", description = "query parameter로 page, size 페이징 처리")
	@GetMapping("/sorted-by-hit")
	public ResponseEntity<?> getBoardsSortedByHit(Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardsSortedByHit(pageable));
	}

	@Operation(summary = "게시글 키워드 조회", description = "query parameter로 keyword 검색 및 page, size 페이징 처리")
	@GetMapping("/search")
	public ResponseEntity<?> getBoardsByKeyword(@RequestParam("keyword") String keyword, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardsByKeyword(keyword, pageable));
	}

	@Operation(summary = "내가 작성한 게시글 목록 조회", description = "query parameter로 page, size 페이징 처리")
	@GetMapping("/my-content")
	public ResponseEntity<?> getBoardsByUser(Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardsByUser(pageable));
	}

	@Operation(summary = "게시글 삭제")
	@DeleteMapping("/{board_id}")
	public ResponseEntity<?> deleteBoard(@PathVariable("board_id") Long boardId) {
		boardService.deleteBoard(boardId);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully deleted board!"));
	}

	@Operation(summary = "게시글 댓글 작성")
	@PostMapping("/{board_id}/comments")
	public ResponseEntity<?> createComment(@PathVariable("board_id") Long boardId,
		@Valid @RequestBody BoardCommentCreateRequest boardCommentCreateRequest) {
		if (BoardValidation.validateKoreanAndNumeric(boardCommentCreateRequest.getCommentContent())) {
			throw new IllegalArgumentException(
				"Comments can only contain Korean letters, numbers, and special characters.");
		}
		BoardCommentResponse boardCommentResponse = boardCommentService.leaveComment(boardId,
			boardCommentCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(boardCommentResponse);
	}

	@Operation(summary = "게시글 댓글 삭제")
	@DeleteMapping("/{board_id}/comments/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable("board_id") Long boardId,
		@PathVariable("comment_id") Long commentId) {
		boardCommentService.deleteComment(boardId, commentId);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully deleted comment!"));
	}

}
