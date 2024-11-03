package com.ssafy.a304.shortgong.global.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.config.NaverTTSConfig;

@SpringBootTest
class ClovaVoiceUtilTest {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private NaverTTSConfig naverTTSConfig;

	@Test
	void requestVoiceByTextAndVoice() {

		ClovaVoiceUtil clovaVoiceUtil = new ClovaVoiceUtil(restTemplate, naverTTSConfig);

		// 예외가 발생하지 않는지 확인
		Assertions.assertDoesNotThrow(() -> {
			String voice = clovaVoiceUtil.requestVoiceByTextAndVoice("안녕 이건 테스트 문장이야", "dsinu-matt");
			Assertions.assertNotNull(voice, "TTS 요청 실패: voice 값이 null입니다.");
		});
	}
}