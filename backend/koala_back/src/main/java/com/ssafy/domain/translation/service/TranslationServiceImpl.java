package com.ssafy.domain.translation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.domain.translation.model.dto.request.GPTRequest;
import com.ssafy.domain.translation.model.dto.request.TranslationRequest;
import com.ssafy.domain.translation.model.dto.response.GPTResponse;
import com.ssafy.domain.translation.model.dto.response.TranslationResponse;

@Service
public class TranslationServiceImpl implements TranslationService {
	@Value("${openai.api.key}")
	private String apiKey;

	private final WebClient webClient = WebClient.builder()
		.baseUrl("https://api.openai.com/v1/")
		.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.build();

	@Override
	public TranslationResponse translate(TranslationRequest translationRequest) {
		GPTRequest gptRequest = new GPTRequest(translationRequest.getLanguage(),
			translationRequest.getOriginSentence());

		GPTResponse translationResult = webClient.method(HttpMethod.POST)
			.uri("chat/completions")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
			.bodyValue(gptRequest)
			.retrieve()
			.bodyToMono(GPTResponse.class)
			.block();

		return TranslationResponse.builder()
			.translationSentence(translationResult.getChoices().get(0).getMessage().getContent())
			.build();
	}
}
