package com.ssafy.domain.image.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.ssafy.domain.image.model.dto.request.GeminiRequest;
import com.ssafy.domain.image.model.dto.response.GeminiResponse;

@HttpExchange("/v1beta/models/")
public interface GeminiInterface {

	@PostExchange("{model}:generateContent")
	GeminiResponse getCompletion(
		@PathVariable String model,
		@RequestBody
		GeminiRequest request
	);
}
