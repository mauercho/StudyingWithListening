package com.ssafy.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.review.model.dto.request.ReviewSaveRequest;
import com.ssafy.domain.review.model.dto.response.ReviewSentenceResponse;
import com.ssafy.domain.review.model.entity.ReviewSentence;
import com.ssafy.domain.review.repository.ReviewRepository;
import com.ssafy.domain.sentence.model.entity.Sentence;
import com.ssafy.domain.sentence.repository.SentenceRepository;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

	private final UserInfoProvider userInfoProvider;
	private final ReviewRepository reviewRepository;
	private final SentenceRepository sentenceRepository;

	@Override
	public List<ReviewSentenceResponse> getReviewSentencesByUserAndKeyword(String keyword, String topic) {
		User currentUser = userInfoProvider.getCurrentUser();

		if (keyword == null && topic == null) {
			return reviewRepository.findAllByUser(currentUser)
				.stream()
				.map(ReviewSentenceResponse::toDto)
				.toList();
		}

		if (keyword == null) {
			return reviewRepository.findAllByTopicAndUser(currentUser, topic)
				.stream()
				.map(ReviewSentenceResponse::toDto)
				.toList();
		}

		if (topic == null) {
			return reviewRepository.findAllByKeywordAndUser(currentUser, keyword)
				.stream()
				.map(ReviewSentenceResponse::toDto)
				.toList();
		}

		return reviewRepository.findAllByKeywordAndTopicAndUser(currentUser, keyword, topic)
			.stream()
			.map(ReviewSentenceResponse::toDto)
			.toList();
	}

	@Override
	@Transactional
	public ReviewSentenceResponse addReviewSentence(ReviewSaveRequest reviewSaveRequest) {
		Sentence sentence = sentenceRepository.findById(reviewSaveRequest.getSentenceId())
			.orElseThrow(() -> new IllegalArgumentException("해당 문장이 존재하지 않습니다."));
		User currentUser = userInfoProvider.getCurrentUser();

		if (reviewRepository.existsByUserAndSentence(currentUser, sentence)) {
			return null;
		}
		return ReviewSentenceResponse.toDto(
			reviewRepository.save(reviewSaveRequest.toReviewSentenceEntity(sentence, currentUser)));
	}

	@Override
	@Transactional
	public void deleteReviewSentence(Long reviewSentenceId) {
		ReviewSentence reviewSentence = reviewRepository.findById(reviewSentenceId)
			.orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

		Sentence sentence = sentenceRepository.findById(reviewSentence.getSentence().getSentenceId())
			.orElseThrow(() -> new IllegalArgumentException("해당 문장이 존재하지 않습니다."));

		User currentUser = userInfoProvider.getCurrentUser();
		if (!reviewSentence.getUser().equals(currentUser)) {
			throw new IllegalArgumentException("해당 리뷰를 삭제할 권한이 없습니다.");
		}

		reviewRepository.delete(reviewSentence);

		if ((sentence.getTopicCategory()).equals("사용자")) {
			sentenceRepository.delete(sentence);
		}

	}

}