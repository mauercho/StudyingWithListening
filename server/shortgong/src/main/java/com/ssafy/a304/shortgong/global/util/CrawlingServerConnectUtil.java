package com.ssafy.a304.shortgong.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingServerConnectUtil {

	private final RestTemplate restTemplate;

	@Value("${fast-api.base-url}")
	private String fastApiBaseUrl;

	public String getBodyTextByUrl(String url) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

		try {
			return restTemplate.exchange(
				fastApiBaseUrl + "?url=" + url,
				HttpMethod.GET,
				request,
				String.class
			).getBody();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
