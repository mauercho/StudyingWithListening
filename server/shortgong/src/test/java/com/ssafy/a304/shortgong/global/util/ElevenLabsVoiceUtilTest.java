package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.model.constant.ElevenLabsVoice.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.config.ElevenLabsTTSConfig;

@SpringBootTest
class ElevenLabsVoiceUtilTest {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ElevenLabsTTSConfig elevenLabsTTSConfig;

	@Test
	void requestVoiceByTextAndVoice() {

		ElevenLabsVoiceUtil elevenLabsVoiceUtil = new ElevenLabsVoiceUtil(restTemplate, elevenLabsTTSConfig);

		Assertions.assertDoesNotThrow(() -> {
			String text = "푸리에 변환에서 시간 영역의 선형 연산은 주파수 영역에서도 대응되는 선형 연산으로 변환됩니다. "
				+ "예를 들어, 시간 영역의 미분은 주파수 영역에서 주파수와의 곱셈으로, 시간 영역의 합성곱은 주파수 영역에서 단순 곱셈으로 변환됩니다. "
				+ "이러한 특성 때문에 복잡한 미분방정식을 푸리에 변환하여 더 쉽게 해결하고, 다시 역변환하는 방식으로 문제를 해결할 수 있습니다.";
			byte[] voiceData = elevenLabsVoiceUtil.requestVoiceByTextAndVoice(text, BRIAN.getVoiceId());
			// 로컬 파일 경로 설정
			String filePath = Paths.get(System.getProperty("user.dir"), "output", "test-voice.mp3").toString();
			File outputFile = new File(filePath);

			// 디렉토리 생성 (존재하지 않을 경우)
			if (!outputFile.getParentFile().exists()) {
				boolean dirsCreated = outputFile.getParentFile().mkdirs();
				if (!dirsCreated) {
					throw new IOException("Failed to create directories for " + filePath);
				}
			}

			// 파일 저장
			try (FileOutputStream fos = new FileOutputStream(outputFile)) {
				fos.write(voiceData);
				System.out.println("MP3 파일 저장 완료: " + filePath);
			} catch (IOException e) {
				throw new RuntimeException("MP3 파일 저장 중 오류 발생: " + e.getMessage(), e);
			}
		});

	}
}