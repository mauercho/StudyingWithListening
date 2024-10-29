package com.ssafy.domain.user.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.user.model.entity.StudyTime;
import com.ssafy.domain.user.repository.StudyTimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyTimeSchedulerServiceImpl implements StudyTimeSchedulerService {

	private final StudyTimeRepository studyTimeRepository;

	@Override
	@Transactional
	@Scheduled(cron = "0 0 0 * * MON")
	public void studyTimeScheduler() {
		List<StudyTime> currentWeekTimes = studyTimeRepository.findByTimeCalTypeBetween(1, 7);

		for (StudyTime currentWeekTime : currentWeekTimes) {
			int currentDayType = currentWeekTime.getTimeCalType();
			Long userId = currentWeekTime.getUserId();

			StudyTime previousWeekTime = studyTimeRepository.findByUserIdAndTimeCalType(
				userId, currentDayType + 7);

			previousWeekTime.setTalkTime(currentWeekTime.getTalkTime());
			previousWeekTime.setSentenceNum(currentWeekTime.getSentenceNum());
			previousWeekTime.setLectureNum(currentWeekTime.getLectureNum());
			studyTimeRepository.save(previousWeekTime);

			currentWeekTime.setTalkTime(0);
			currentWeekTime.setSentenceNum(0);
			currentWeekTime.setLectureNum(0);
			studyTimeRepository.save(currentWeekTime);

		}

	}

}
