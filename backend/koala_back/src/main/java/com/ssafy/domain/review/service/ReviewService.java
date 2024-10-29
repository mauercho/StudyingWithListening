package com.ssafy.domain.review.service;

import java.util.List;

import com.ssafy.domain.review.model.dto.request.ReviewSaveRequest;
import com.ssafy.domain.review.model.dto.response.ReviewSentenceResponse;

public interface ReviewService {

	List<ReviewSentenceResponse> getReviewSentencesByUserAndKeyword(String keyword, String topic);

	ReviewSentenceResponse addReviewSentence(ReviewSaveRequest reviewSaveRequest);

	void deleteReviewSentence(Long reviewSentenceId);

}
