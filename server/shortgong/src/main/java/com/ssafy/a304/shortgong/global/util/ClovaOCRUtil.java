package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.ClovaErrorCode.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a304.shortgong.global.config.NaverOCRConfig;
import com.ssafy.a304.shortgong.global.error.CustomException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public class ClovaOCRUtil {

	private final RestTemplate restTemplate;

	private final NaverOCRConfig naverOCRConfig;

	public List<String> requestOCR(List<String> imageUrls) throws CustomException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("X-OCR-SECRET", naverOCRConfig.getOcrSecret());

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("version", "V2");
		requestBody.put("requestId", RandomUtil.generateUUID());
		requestBody.put("timestamp", System.currentTimeMillis());

		List<Map<String, Object>> images = new ArrayList<>();
		for (int i = 0; i < imageUrls.size(); i++) {
			Map<String, Object> image = new HashMap<>();
			image.put("format", FileUtil.getExtensionString(imageUrls.get(i)));
			image.put("name", "image_" + (i + 1));
			image.put("url", imageUrls.get(i));
			images.add(image);
		}

		requestBody.put("images", images);
		requestBody.put("enableTableDetection", false); // 테이블 감지 비활성화

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

		try {
			String response = restTemplate.exchange(
				naverOCRConfig.getNaverOCRUrl(),
				HttpMethod.POST,
				request,
				String.class
			).getBody();

			return parseSentencesFromResponse(response);
		} catch (RestClientException e) {
			log.debug("{} : {}", NAVER_CLOVA_OCR_REQUEST_FAIL.getMessage(), e.getMessage());
			// TODO: 커스텀 Exception 변경하기
			throw new IllegalAccessError(e.getMessage());
		}
	}

	private List<String> parseSentencesFromResponse(String response) throws CustomException {

		List<String> sentences = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = objectMapper.readTree(response);
		} catch (JsonProcessingException e) {
			// TODO: Custom Exception
			throw new IllegalArgumentException(e);
		}

		// images 배열에서 첫 번째 항목 가져오기
		JsonNode imagesNode = root.get("images");
		if (imagesNode == null || !imagesNode.isArray()) {
			throw new CustomException(NAVER_CLOVA_OCR_REQUEST_IMAGE_NULL);
		}
		for (JsonNode imageNode : imagesNode) {
			JsonNode fieldsNode = imageNode.get("fields");
			if (fieldsNode == null || !fieldsNode.isArray()) {
				// TODO: 정확한 Exception 던지기
				throw new CustomException(NAVER_CLOVA_OCR_REQUEST_IMAGE_NULL);
			}

			StringBuilder completeText = new StringBuilder();

			for (JsonNode fieldNode : fieldsNode) {
				String inferText = fieldNode.get("inferText").asText();
				completeText.append(inferText).append(" "); // 단어 간 공백 추가
			}

			Pattern pattern = Pattern.compile("[^.!?]+[.!?]");
			Matcher matcher = pattern.matcher(completeText.toString().trim());
			while (matcher.find()) {
				String sentence = matcher.group().trim();
				if (sentence.isEmpty()) {
					continue;
				}
				sentences.add(sentence);
			}
		}
		return sentences;

	}
}
