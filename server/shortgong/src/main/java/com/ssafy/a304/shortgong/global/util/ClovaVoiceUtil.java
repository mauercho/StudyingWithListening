package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.ClovaErrorCode.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.config.NaverTTSConfig;
import com.ssafy.a304.shortgong.global.error.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClovaVoiceUtil {

	private final RestTemplate restTemplate;

	private final NaverTTSConfig naverTTSConfig;

	public byte[] requestVoiceByTextAndVoice(String text, String voice) throws CustomException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("X-NCP-APIGW-API-KEY-ID", naverTTSConfig.getClientId());
		headers.add("X-NCP-APIGW-API-KEY", naverTTSConfig.getClientSecret());

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("speaker", voice);
		params.add("volume", "0");
		params.add("speed", "0");
		params.add("pitch", "0");
		params.add("format", "mp3");
		params.add("text", text);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		try {
			return restTemplate.exchange(
				naverTTSConfig.getNaverTTSUrl(),
				HttpMethod.POST,
				request,
				byte[].class
			).getBody();
		} catch (Exception e) {
			log.debug("{} : {}", NAVER_CLOVA_TTS_REQUEST_FAIL.getMessage(), e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
			// throw new CustomException(NAVER_CLOVA_TTS_REQUEST_FAIL);
		}
	}

}
