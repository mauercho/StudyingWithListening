package com.ssafy.domain.sentence.service;

import java.util.List;

import com.ssafy.domain.review.model.dto.response.ReviewSentenceResponse;
import com.ssafy.domain.sentence.model.dto.request.SentenceCreateRequest;
import com.ssafy.domain.sentence.model.dto.request.SentenceTestRequest;
import com.ssafy.domain.sentence.model.dto.response.LectureSentenceResponse;
import com.ssafy.domain.sentence.model.dto.response.SentenceDictationResponse;
import com.ssafy.domain.sentence.model.dto.response.SentenceTestLeavesResponse;

public interface SentenceService {

	List<SentenceDictationResponse> getRandomSentence(String topic);

	SentenceTestLeavesResponse testWritingPaper(List<SentenceTestRequest> writingPaper);

	ReviewSentenceResponse createSentence(SentenceCreateRequest sentenceCreateRequest);

	String makeResultTag(String correct, String answer);

	List<LectureSentenceResponse> getLectureSentences(Long lectureId);

}

