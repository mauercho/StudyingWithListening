package com.ssafy.domain.image.service;

import org.springframework.stereotype.Service;

import com.ssafy.domain.image.model.dto.request.GeminiRequest;
import com.ssafy.domain.image.model.dto.response.GeminiResponse;

@Service
public class GeminiService {
	public static final String GEMINI_PRO = "gemini-pro";
	public static final String GEMINI_ULTIMATE = "gemini-ultimate";
	public static final String GEMINI_PRO_VISION = "gemini-pro-vision";
	public static final String GEMINI_FLASH = "gemini-1.5-flash-latest";

	private final GeminiInterface geminiInterface;

	public GeminiService(GeminiInterface geminiInterface) {
		this.geminiInterface = geminiInterface;
	}

	public GeminiResponse getCompletionWithImage(GeminiRequest request) {
		return geminiInterface.getCompletion(GEMINI_FLASH, request);
	}

	public String getCompletionWithImage(String text, GeminiRequest.InlineData inlineData) {
		GeminiRequest geminiRequest = new GeminiRequest(text, inlineData);
		GeminiResponse response = getCompletionWithImage(geminiRequest);

		return response.getCandidates()
			.stream()
			.findFirst()
			.flatMap(candidate -> candidate.getContent().getParts()
				.stream()
				.findFirst()
				.map(GeminiResponse.TextPart::getText))
			.orElse(null);
	}
}
