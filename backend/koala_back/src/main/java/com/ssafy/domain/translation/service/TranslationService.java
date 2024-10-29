package com.ssafy.domain.translation.service;

import com.ssafy.domain.translation.model.dto.request.TranslationRequest;
import com.ssafy.domain.translation.model.dto.response.TranslationResponse;

public interface TranslationService {
	TranslationResponse translate(TranslationRequest translationRequest);
}
