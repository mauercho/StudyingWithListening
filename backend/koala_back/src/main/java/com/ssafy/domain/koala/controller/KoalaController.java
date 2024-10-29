package com.ssafy.domain.koala.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.koala.model.dto.request.KoalaNameRequest;
import com.ssafy.domain.koala.service.KoalaService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/koalas")
public class KoalaController {

	private final KoalaService koalaService;

	@Operation(summary = "코알라 정보 조회")
	@GetMapping
	public ResponseEntity<?> getKoala() {
		return ResponseEntity.status(HttpStatus.OK).body(koalaService.getKoala());
	}

	@Operation(summary = "코알라 이름 변경")
	@PatchMapping("/{koala_id}")
	public ResponseEntity<?> updateKoalaName(@PathVariable("koala_id") Long koalaId,
		@Valid @RequestBody KoalaNameRequest koalaNameRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(koalaService.changeKoalaName(koalaNameRequest, koalaId));
	}

	@Operation(summary = "코알라 먹이 주기")
	@GetMapping("/{koala_id}/leaves")
	public ResponseEntity<?> updateKoalaExp(@PathVariable("koala_id") Long koalaId) {
		return ResponseEntity.status(HttpStatus.OK).body(koalaService.increaseKoalaExp(koalaId));
	}

}
