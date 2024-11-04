package com.ssafy.a304.shortgong.global.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.config.NaverOCRConfig;

@SpringBootTest
class ClovaOCRUtilTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private NaverOCRConfig naverOCRConfig;

	@Test
	void requestOCR() {

		ClovaOCRUtil clovaOCRUtil = new ClovaOCRUtil(restTemplate, naverOCRConfig);
		List<String> imageUrls = new ArrayList<>();

		imageUrls.add(
			"https://shortgong.s3.us-east-1.amazonaws.com/upload-content/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4%EC%84%A4%EB%AA%85.PNG");
		// 예외가 발생하지 않는지 확인
		Assertions.assertDoesNotThrow(() -> {
			List<String> response = clovaOCRUtil.requestOCR(imageUrls);

			Assertions.assertNotNull(response, "ocr 요청 실패: response 값이 null입니다.");
		});
	}
}