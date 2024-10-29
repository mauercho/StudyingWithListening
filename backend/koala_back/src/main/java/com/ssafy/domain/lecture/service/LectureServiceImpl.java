package com.ssafy.domain.lecture.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.lecture.model.dto.request.LectureNoteRequest;
import com.ssafy.domain.lecture.model.dto.response.LectureNoteResponse;
import com.ssafy.domain.lecture.model.dto.response.LectureResponse;
import com.ssafy.domain.lecture.model.dto.response.RegisteredLectureResponse;
import com.ssafy.domain.lecture.model.entity.Lecture;
import com.ssafy.domain.lecture.model.entity.RegisteredLecture;
import com.ssafy.domain.lecture.model.entity.RegisteredLectureId;
import com.ssafy.domain.lecture.repository.LectureNoteRepository;
import com.ssafy.domain.lecture.repository.LectureRepository;
import com.ssafy.domain.lecture.repository.RegisteredLectureRepository;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.UserRepository;
import com.ssafy.domain.user.service.StudyTimeService;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureServiceImpl implements LectureService {

	private final UserRepository userRepository;
	private final StudyTimeService studyTimeService;
	private final LectureRepository lectureRepository;
	private final LectureNoteRepository lectureNoteRepository;
	private final RegisteredLectureRepository registeredLectureRepository;
	private final UserInfoProvider userInfoProvider;

	@Override
	public List<LectureResponse> getAllLecture() {
		List<Lecture> lectures = lectureRepository.findAllByIsOpen();
		return lectures.stream().map(LectureResponse::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void registerLecture(Long lectureId) {
		Optional<Lecture> lecture = lectureRepository.findById(lectureId);

		if (!registeredLectureRepository.existsById(
			new RegisteredLectureId(userInfoProvider.getCurrentUserId(), lectureId)) && lecture.isPresent()) {
			RegisteredLecture registeredLecture = new RegisteredLecture();

			registeredLecture.setUser(userInfoProvider.getCurrentUser());
			registeredLecture.setLecture(lecture.get());
			registeredLecture.setId(new RegisteredLectureId(userInfoProvider.getCurrentUserId(), lectureId));

			registeredLectureRepository.save(registeredLecture);
			studyTimeService.increaseLectureCount();

			User user = userInfoProvider.getCurrentUser();
			user.increaseUserLeaves(1);
			userRepository.save(user);
		}
	}

	@Override
	public List<RegisteredLectureResponse> getRegisteredLecture() {
		List<RegisteredLecture> registeredLectures = registeredLectureRepository.findAllByUser(
			userInfoProvider.getCurrentUser());

		List<RegisteredLectureResponse> registeredLectureResponses = new ArrayList<>();
		for (RegisteredLecture registeredLecture : registeredLectures) {
			Long lectureNoteCount = lectureNoteRepository.countByUserIdAndLectureId(
				userInfoProvider.getCurrentUser(), registeredLecture.getLecture());
			registeredLectureResponses.add(RegisteredLectureResponse.toDto(registeredLecture, lectureNoteCount));
		}
		return registeredLectureResponses;
	}

	@Override
	@Transactional
	public LectureNoteResponse writeLectureNote(LectureNoteRequest lectureNoteRequest) {
		Optional<Lecture> lecture = lectureRepository.findById(lectureNoteRequest.getLectureId());
		return lecture.map(value -> LectureNoteResponse.toDto(
				lectureNoteRepository.save(lectureNoteRequest.toEntity(userInfoProvider.getCurrentUser(), value))))
			.orElse(null);
	}

	@Override
	public List<LectureNoteResponse> getLectureNote(Long lectureId) {
		return lectureNoteRepository.findByLectureId(userInfoProvider.getCurrentUser(),
				lectureRepository.findById(lectureId).orElse(null)).stream()
			.map(LectureNoteResponse::toDto)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public boolean deleteLectureNote(Long lectureId) {
		if (lectureNoteRepository.existsById(lectureId)) {
			lectureNoteRepository.deleteById(lectureId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public LectureResponse setSessionId(Long lectureId, String sessionId) {
		Optional<Lecture> lecture = lectureRepository.findById(lectureId);
		if (lecture.isPresent()) {
			lecture.get().setSessionId(sessionId);
			return LectureResponse.toDto(lectureRepository.save(lecture.get()));
		}
		return null;
	}

	@Override
	public String getSessionId(Long lectureId) {
		Optional<Lecture> lecture = lectureRepository.findById(lectureId);
		return lecture.map(Lecture::getSessionId).orElse(null);
	}
}
