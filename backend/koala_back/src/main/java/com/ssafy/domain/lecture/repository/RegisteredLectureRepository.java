package com.ssafy.domain.lecture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.domain.lecture.model.entity.RegisteredLecture;
import com.ssafy.domain.lecture.model.entity.RegisteredLectureId;
import com.ssafy.domain.user.model.entity.User;

public interface RegisteredLectureRepository extends JpaRepository<RegisteredLecture, RegisteredLectureId> {

	@Query("SELECT r FROM RegisteredLecture r WHERE r.user = :user")
	List<RegisteredLecture> findAllByUser(@Param("user") User user);

}
