package com.ssafy.domain.sentence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.review.model.dto.request.ReviewSaveRequest;
import com.ssafy.domain.review.model.dto.response.ReviewSentenceResponse;
import com.ssafy.domain.review.model.entity.ReviewSentence;
import com.ssafy.domain.review.repository.ReviewRepository;
import com.ssafy.domain.review.service.ReviewService;
import com.ssafy.domain.sentence.model.dto.request.SentenceCreateRequest;
import com.ssafy.domain.sentence.model.dto.request.SentenceTestRequest;
import com.ssafy.domain.sentence.model.dto.response.LectureSentenceResponse;
import com.ssafy.domain.sentence.model.dto.response.SentenceDictationResponse;
import com.ssafy.domain.sentence.model.dto.response.SentenceTestLeavesResponse;
import com.ssafy.domain.sentence.model.dto.response.SentenceTestResponse;
import com.ssafy.domain.sentence.model.entity.Sentence;
import com.ssafy.domain.sentence.repository.LectureSentenceRepository;
import com.ssafy.domain.sentence.repository.SentenceRepository;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.UserRepository;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final UserInfoProvider userInfoProvider;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final SentenceRepository sentenceRepository;
	private final LectureSentenceRepository lectureSentenceRepository;
	private final ReviewService reviewService;

	@Override
	@Transactional
	public List<SentenceDictationResponse> getRandomSentence(String topic) {
		List<Sentence> sentences;
		if (topic == null || topic.isEmpty()) {
			sentences = sentenceRepository.findRandomSentences(userInfoProvider.getCurrentUserId());
		} else if (topic.equals("사용자")) {
			sentences = sentenceRepository.findRandomSentencesByUser(userInfoProvider.getCurrentUserId());
		} else {
			sentences = sentenceRepository.findRandomSentencesByTopic(topic);
		}

		return sentences.stream().map(SentenceDictationResponse::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public SentenceTestLeavesResponse testWritingPaper(List<SentenceTestRequest> writingPaper) {
		List<SentenceTestResponse> sentenceTestResponses = new ArrayList<>();
		List<ReviewSentence> reviewSentences = new ArrayList<>();
		User user = userInfoProvider.getCurrentUser();

		int leaves = 0;

		for (SentenceTestRequest request : writingPaper) {
			Optional<Sentence> originSentence = sentenceRepository.findById(request.getSentenceId());
			if (originSentence.isEmpty()) {
				return null;
			}

			String originText = originSentence.get().getSentenceText();
			String userText = request.getUserSentence();
			String resultTag;
			boolean correct;

			if (originText.equals(userText)) {
				resultTag = userText;
				correct = true;
				if (request.isToggled()) {
					leaves += 1;
				} else {
					leaves += 2;
				}
			} else {
				resultTag = makeResultTag(originText, userText);
				reviewSentences.add(ReviewSaveRequest.builder()
					.sentenceId(request.getSentenceId())
					.build()
					.toReviewSentenceEntity(originSentence.get(), user));
				correct = false;
			}

			sentenceTestResponses.add(SentenceTestResponse.toDto(originText, userText, resultTag, correct,
				reviewRepository.existsByUserAndSentence(user, originSentence.get())));
		}

		// 1. 복습페이지에 틀린 문장 저장
		for (ReviewSentence reviewSentence : reviewSentences) {
			reviewService.addReviewSentence(
				ReviewSaveRequest.builder().sentenceId(reviewSentence.getSentence().getSentenceId()).build());
		}
		// 2. 유칼립투스 증가
		// 문제 별로 토글을 키고 했다면 -> 1개
		// 문제 별로 토글을 끄고 했다면 -> 2개
		user.increaseUserLeaves(leaves);
		userRepository.save(user);

		return SentenceTestLeavesResponse.toDto(sentenceTestResponses, leaves);
	}

	@Override
	@Transactional
	public ReviewSentenceResponse createSentence(SentenceCreateRequest sentenceCreateRequest) {
		User user = userInfoProvider.getCurrentUser();
		Sentence sentence = sentenceCreateRequest.toEntity(user);
		sentenceRepository.save(sentence);

		ReviewSaveRequest reviewSaveRequest = ReviewSaveRequest.builder().sentenceId(sentence.getSentenceId()).build();

		return ReviewSentenceResponse.toDto(
			reviewRepository.save(reviewSaveRequest.toReviewSentenceEntity(sentence, user)));
	}

	@Override
	public String makeResultTag(String originText, String userText) {
		StringBuilder result = new StringBuilder();
		int correctIndex = 0;
		int answerIndex = 0;
		while (correctIndex < originText.length() && answerIndex < userText.length()) {
			char correctChar = originText.charAt(correctIndex);
			char answerChar = userText.charAt(answerIndex);

			if (correctChar == answerChar) {
				result.append(correctChar);  // 정답인 경우
				correctIndex++;
				answerIndex++;
			} else {
				if (correctChar == ' ') {
					// 정답에서는 공백인데 답안이 공백이 아닌 경우
					result.append("<span className='text-extra-char'> </span>");
					correctIndex++;
				} else if (answerChar == ' ') {
					// 정답에서는 공백이 아닌데 답안이 공백인 경우
					result.append("<span className='text-missing-char'> </span>");
					answerIndex++;
				} else {
					// 글자 자체가 틀린 경우
					result.append("<span className='text-char-error'>").append(answerChar).append("</span>");
					correctIndex++;
					answerIndex++;
				}
			}
		}

		// 남은 답안 인덱스 처리
		while (answerIndex < userText.length()) {
			result.append("<span className='text-char-error'>").append(userText.charAt(answerIndex)).append("</span>");
			answerIndex++;
		}

		if (userText.length() < originText.length()) {
			correctIndex = userText.length();
			while (correctIndex < originText.length()) {
				result.append("<span className='text-char-error'>")
					.append(originText.charAt(correctIndex))
					.append("</span>");
				correctIndex++;
			}
		}

		return result.toString();
	}

	@Override
	public List<LectureSentenceResponse> getLectureSentences(Long lectureId) {
		User user = userInfoProvider.getCurrentUser();

		return lectureSentenceRepository.findByLectureId(lectureId)
			.stream()
			.map(lectureSentence -> LectureSentenceResponse.toDto(lectureSentence,
				reviewRepository.existsByUserAndSentence(user, lectureSentence.getSentence())))
			.collect(Collectors.toList());
	}
}
