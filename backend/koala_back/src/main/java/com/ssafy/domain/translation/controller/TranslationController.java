package com.ssafy.domain.translation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.translation.model.dto.request.TranslationRequest;
import com.ssafy.domain.translation.service.TranslationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/translation")
public class TranslationController {
	private final TranslationService translationService;

	@PostMapping
	public ResponseEntity<?> getTranslation(@Valid @RequestBody TranslationRequest translationRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(translationService.translate(translationRequest));
	}
}
