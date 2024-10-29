package com.ssafy.domain.image.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.domain.image.service.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

	private final ImageService imageService;

	@Operation(summary = "image에서 text 추출")
	@PostMapping
	public ResponseEntity<?> getTextFromImage(@RequestParam("file") MultipartFile file) throws IOException {
		return ResponseEntity.status(HttpStatus.CREATED).body(imageService.imageToText(file));
	}
}
