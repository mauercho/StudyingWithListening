package com.ssafy.a304.shortgong.global.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ClaudeUtilTest {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void sendMessage() {

		// Assertions.assertDoesNotThrow(() -> {
		// 	ClaudeUtil claudeUtil = new ClaudeUtil(restTemplate);
		// 	ClaudeResponse response = claudeUtil.sendMessage("안녕하세요");
		//
		// 	System.out.println(response.getContent());
		// 	Assertions.assertNotNull(response.getContent(), "ocr 요청 실패: response 값이 null입니다.");
		// });
	}
}