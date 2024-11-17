package com.ssafy.a304.shortgong.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ElevenLabsTTSConfig {

	@Value("${eleven-labs.api.keys.key-1}")
	private String apiKey1;

	@Value("${eleven-labs.api.keys.key-2}")
	private String apiKey2;
	
	@Value("${eleven-labs.api.keys.key-3}")
	private String apiKey3;

	@Value("${eleven-labs.api.tts.url}")
	private String url;
}
