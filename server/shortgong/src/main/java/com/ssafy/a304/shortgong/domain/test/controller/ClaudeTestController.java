package com.ssafy.a304.shortgong.domain.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping("/test")
	public void testClaudeApi2() {
		claudeTestService.testAnswerClaudeApi();
	}

}
