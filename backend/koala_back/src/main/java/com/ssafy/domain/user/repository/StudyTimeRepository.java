package com.ssafy.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.domain.user.model.entity.StudyTime;
import com.ssafy.domain.user.model.entity.StudyTimeId;

public interface StudyTimeRepository extends JpaRepository<StudyTime, StudyTimeId> {

	StudyTime findByUserIdAndTimeCalType(Long userId, Integer timeCalType);

	List<StudyTime> findByTimeCalTypeBetween(int start, int end);

}
