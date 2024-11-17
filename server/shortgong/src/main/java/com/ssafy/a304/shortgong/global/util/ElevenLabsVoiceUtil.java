package com.ssafy.a304.shortgong.global.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.config.ElevenLabsTTSConfig;
import com.ssafy.a304.shortgong.global.error.CustomException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenLabsVoiceUtil {

	private final RestTemplate restTemplate;
	private final ElevenLabsTTSConfig elevenLabsTTSConfig;

	private int apiKeyIndex = 0;
	private String[] apiKeys;

	public void requestVoiceByTextAndVoiceAsync(String text, String voiceId, ElevenLabsVoiceUtil.Callback callback) {

		try {
			byte[] voice = requestVoiceByTextAndVoice(text, voiceId);
			callback.onSuccess(voice);
		} catch (Exception e) {
			callback.onError(e);
		}
	}

	public byte[] requestVoiceByTextAndVoice(String text, String voiceId) throws CustomException {

		try {
			return restTemplate.exchange(
				elevenLabsTTSConfig.getUrl() + voiceId,
				HttpMethod.POST,
				new HttpEntity<>(getBody(text), getHeaders()),
				byte[].class
			).getBody();
		} catch (Exception e) {
			log.debug("Eleven Labs 보이스 요청 실패 : {}", e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public interface Callback {

		void onSuccess(byte[] voice);

		void onError(Exception e);
	}

	private Map<String, Object> getVoiceSettings() {

		Map<String, Object> voiceSettings = new HashMap<>();
		voiceSettings.put("stability", 1);
		voiceSettings.put("similarity_boost", 1);
		voiceSettings.put("style", 1);
		voiceSettings.put("use_speaker_boost", true);
		return voiceSettings;
	}

	private Map<String, Object> getBody(String text) {

		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("text", text);
		bodyMap.put("model_id", "eleven_turbo_v2_5");
		bodyMap.put("language_code", "ko");
		bodyMap.put("voice_settings", getVoiceSettings());
		// bodyMap.put("pronunciation_dictionary_locators", List.of(Map.of(
		// 	"pronunciation_dictionary_id", "<string>",
		// 	"version_id", "<string>"
		// )));
		bodyMap.put("seed", 123);
		// bodyMap.put("previous_text", "<string>");
		// bodyMap.put("next_text", "<string>");
		// bodyMap.put("previous_request_ids", List.of("<string>"));
		// bodyMap.put("next_request_ids", List.of("<string>"));
		bodyMap.put("use_pvc_as_ivc", true);
		bodyMap.put("apply_text_normalization", "auto");

		return bodyMap;
	}

	private HttpHeaders getHeaders() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "audio/mpeg");
		headers.set("xi-api-key", getApiKey());
		return headers;
	}

	private String getApiKey() {

		apiKeyIndex = (apiKeyIndex + 1) % apiKeys.length;
		return apiKeys[apiKeyIndex];
	}

	@PostConstruct
	private void initializeApiKeys() {

		apiKeys = new String[] {
			elevenLabsTTSConfig.getApiKey1(),
			elevenLabsTTSConfig.getApiKey2(),
			elevenLabsTTSConfig.getApiKey3()};

	}

}
