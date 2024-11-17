package com.ssafy.a304.shortgong.domain.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a304.shortgong.domain.test.service.ClaudeTestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/claude-test")
public class ClaudeTestController {

	private final ClaudeTestService claudeTestService;

	@GetMapping
	public void testClaudeApi() {

		claudeTestService.testTPQClaudeApi();
	}

	@GetMapping("/answer")
	public void testClaudeApi2() {

		claudeTestService.testAnswerClaudeApi();
	}

	@GetMapping("/pdf")
	public void testPdf() throws Exception {

		claudeTestService.testPdf();
	}

	@GetMapping("/image")
	public void testImage() throws Exception {

		claudeTestService.testImage();
	}

	@GetMapping("/keyword/TPQ")
	public void testKeyword() {

		claudeTestService.testKeyword();
	}

	@GetMapping("/keyword-answer")
	public void testKeywordAnswer(@RequestParam(name = "T") String T, @RequestParam(name = "P") String P, @RequestParam(name = "Q") String Q,
		@RequestParam(name = "keyword") String keyword) {

		claudeTestService.testKeywordAnswer(T, P, Q, keyword);
	}

}
