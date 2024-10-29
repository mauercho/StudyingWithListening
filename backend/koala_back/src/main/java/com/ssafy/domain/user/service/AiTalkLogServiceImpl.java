package com.ssafy.domain.user.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.user.model.entity.AiTalkLog;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.AiTalkLogRepository;
import com.ssafy.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AiTalkLogServiceImpl implements AiTalkLogService {

	private final UserRepository userRepository;
	private final AiTalkLogRepository aiTalkLogRepository;

	@Override
	@Transactional
	public void initAiTalkLog(User user) {
		Optional<AiTalkLog> aiTalkLog = aiTalkLogRepository.findByUserId(user.getUserId());
		if (aiTalkLog.isPresent()) {
			aiTalkLog.get().setStartTime(null);
			aiTalkLog.get().setEndTime(null);
			aiTalkLogRepository.save(aiTalkLog.get());
		} else {
			aiTalkLogRepository.save(AiTalkLog.builder().user(user).build());
		}
	}

	@Override
	@Transactional
	public void createStartTimeLog(Long userId) {
		Optional<AiTalkLog> aiTalkLog = aiTalkLogRepository.findByUserId(userId);
		if (aiTalkLog.isPresent()) {
			aiTalkLog.get().setStartTime(LocalDateTime.now());
			aiTalkLogRepository.save(aiTalkLog.get());
		} else {
			initAiTalkLog(userRepository.findById(userId).get());
			createStartTimeLog(userId);
		}
	}

	@Override
	@Transactional
	public void createEndTimeLog(Long userId) {
		Optional<AiTalkLog> aiTalkLog = aiTalkLogRepository.findByUserId(userId);
		if (aiTalkLog.isPresent()) {
			aiTalkLog.get().setEndTime(LocalDateTime.now());
			aiTalkLogRepository.save(aiTalkLog.get());
		} else {
			throw new IllegalArgumentException("aiTalkLog가 존재하지 않습니다.");
		}
	}

	@Override
	public Integer calculateTalkTime(Long userId) {
		Optional<AiTalkLog> aiTalkLog = aiTalkLogRepository.findByUserId(userId);
		if (aiTalkLog.isPresent()) {
			if (aiTalkLog.get().getStartTime() != null && aiTalkLog.get().getEndTime() != null) {
				Duration duration = Duration.between(aiTalkLog.get().getStartTime(), aiTalkLog.get().getEndTime());
				initAiTalkLog(userRepository.findById(userId).get());
				return (int)duration.toMinutes();
			}
			initAiTalkLog(userRepository.findById(userId).get());
		}
		return 0;
	}

}
