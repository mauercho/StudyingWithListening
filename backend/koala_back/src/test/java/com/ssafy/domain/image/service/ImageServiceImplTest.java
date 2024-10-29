package com.ssafy.domain.image.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ImageServiceImplTest {

	private final ImageServiceImpl imageServiceImpl = new ImageServiceImpl(null, null);

	@Test
	void checkSpelling() throws IOException {
		String testText = "안냥하세요.";
		String expectedResponse = "안녕하세요.";
		String passportKey = null;
		try {
			passportKey = imageServiceImpl.readKey();
			imageServiceImpl.checkSpelling("테스트 문장", passportKey);
		} catch (IOException e) {
			passportKey = imageServiceImpl.updateKey();
		}

		try {
			String result = imageServiceImpl.checkSpelling(testText, passportKey);
			assertNotNull(result);
			assertTrue(result.contains(expectedResponse)); // 예상된 결과가 포함되어 있는지 확인
		} catch (IOException e) {
			fail("IOException 발생: " + e.getMessage());
		}
	}
}