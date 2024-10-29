package com.ssafy.domain.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.domain.board.model.entity.Board;

public interface BoardImageService {

	List<String> saveBoardImages(Long boardId, List<MultipartFile> boardImages) throws IOException;

	String saveBoardImage(MultipartFile multipartFile, Board board);

}
