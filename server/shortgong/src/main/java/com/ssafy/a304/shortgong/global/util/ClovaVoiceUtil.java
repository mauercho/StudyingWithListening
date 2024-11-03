package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.ClovaErrorCode.*;

import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.config.NaverTTSConfig;
import com.ssafy.a304.shortgong.global.error.CustomException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public class ClovaVoiceUtil {

	private final RestTemplate restTemplate;

	private final NaverTTSConfig naverTTSConfig;

	public String requestVoiceByTextAndVoice(String text, String voice) throws CustomException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("X-NCP-APIGW-API-KEY-ID", naverTTSConfig.getClientId());
		headers.add("X-NCP-APIGW-API-KEY", naverTTSConfig.getClientSecret());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

		try {
			String postParams =
				"speaker=" + voice
					+ "&volume=0"
					+ "&speed=0"
					+ "&pitch=0"
					+ "&format=mp3"
					+ "&text=" + URLEncoder.encode(text, "UTF-8");
			return restTemplate.exchange(
				naverTTSConfig.getNaverTTSUrl() + postParams,
				HttpMethod.POST,
				request,
				String.class
			).getBody();
		} catch (Exception e) {
			log.debug("{} : {}", NAVER_CLOVA_TTS_REQUEST_FAIL.getMessage(), e.getMessage());
			throw new IllegalAccessError(e.getMessage());
			// throw new CustomException(NAVER_CLOVA_TTS_REQUEST_FAIL);
		}
	}

}
