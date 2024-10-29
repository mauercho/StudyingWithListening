package com.ssafy.domain.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.user.model.dto.response.StudyTimeByDayResponse;
import com.ssafy.domain.user.model.dto.response.StudyTimeResponse;
import com.ssafy.domain.user.model.dto.response.TotalStudyTimeResponse;
import com.ssafy.domain.user.model.entity.StudyTime;
import com.ssafy.domain.user.repository.StudyTimeRepository;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyTimeServiceImpl implements StudyTimeService {

	private final UserInfoProvider userInfoProvider;
	private final StudyTimeRepository studyTimeRepository;
	private final AiTalkLogService aiTalkLogService;

	@Override
	public TotalStudyTimeResponse getStudyTime() {
		Long userId = userInfoProvider.getCurrentUserId();

		List<StudyTimeResponse> thisStudyTimes = new ArrayList<>();
		for (int i = 1; i < 8; i++) {
			thisStudyTimes.add(StudyTimeResponse.toDto(studyTimeRepository.findByUserIdAndTimeCalType(userId, i)));
		}
		StudyTimeByDayResponse thisWeek = StudyTimeByDayResponse.toDto(thisStudyTimes);

		List<StudyTimeResponse> lastStudyTimes = new ArrayList<>();
		for (int i = 8; i < 15; i++) {
			lastStudyTimes.add(StudyTimeResponse.toDto(studyTimeRepository.findByUserIdAndTimeCalType(userId, i)));
		}
		StudyTimeByDayResponse lastWeek = StudyTimeByDayResponse.toDto(lastStudyTimes);

		StudyTimeResponse total = StudyTimeResponse.toDto(studyTimeRepository.findByUserIdAndTimeCalType(userId, 0));

		return TotalStudyTimeResponse.toDto(lastWeek, thisWeek, total, LocalDateTime.now().getDayOfWeek());
	}

	@Override
	@Transactional
	public void initStudyTime(Long userId) {
		/*총 시간(0)
		이번 주: 월(1)/화(2)/수(3)/목(4)/금(5)/토(6)/일(7)
		지난주: 월(8)/화(9)/수(10)/목(11)/금(12)/토(13)/일(14)*/

		for (int i = 0; i < 15; i++) {
			StudyTime studyTime = StudyTime.builder()
				.timeCalType(i)
				.userId(userId)
				.talkTime(0)
				.sentenceNum(0)
				.lectureNum(0)
				.build();
			studyTimeRepository.save(studyTime);
		}
	}

	@Override
	@Transactional
	public void increaseDictationCount(Integer dictationCount) {
		Long userId = userInfoProvider.getCurrentUserId();
		Integer day = LocalDateTime.now().getDayOfWeek().getValue();
		StudyTime studyTime = studyTimeRepository.findByUserIdAndTimeCalType(userId, day);
		studyTime.increaseSentenceNum(dictationCount);
		studyTimeRepository.save(studyTime);

		increaseTotalStudyTime(userId, 1, dictationCount);
	}

	@Override
	@Transactional
	public void increaseLectureCount() {
		Long userId = userInfoProvider.getCurrentUserId();
		Integer day = LocalDateTime.now().getDayOfWeek().getValue();
		StudyTime studyTime = studyTimeRepository.findByUserIdAndTimeCalType(userId, day);
		studyTime.increaseLectureNum();
		studyTimeRepository.save(studyTime);

		increaseTotalStudyTime(userId, 2, 1);
	}

	@Override
	@Transactional
	public Integer increaseAiTalkMinutes() {
		Long userId = userInfoProvider.getCurrentUserId();
		Integer day = LocalDateTime.now().getDayOfWeek().getValue();
		StudyTime studyTime = studyTimeRepository.findByUserIdAndTimeCalType(userId, day);
		Integer talkMinutes = aiTalkLogService.calculateTalkTime(userId);
		studyTime.increaseTalkTime(talkMinutes);
		studyTimeRepository.save(studyTime);

		increaseTotalStudyTime(userId, 0, talkMinutes);
		return talkMinutes;
	}

	@Override
	@Transactional
	public void increaseTotalStudyTime(Long userId, Integer studyType, Integer studyTime) {
		StudyTime userStudyTime = studyTimeRepository.findByUserIdAndTimeCalType(userId, 0);
		switch (studyType) {
			case 0 -> // AI 회화
				userStudyTime.increaseTalkTime(studyTime);
			case 1 -> // 받아쓰기
				userStudyTime.increaseSentenceNum(studyTime);
			case 2 -> // 강의 수
				userStudyTime.increaseLectureNum();
		}
		studyTimeRepository.save(userStudyTime);
	}

}
