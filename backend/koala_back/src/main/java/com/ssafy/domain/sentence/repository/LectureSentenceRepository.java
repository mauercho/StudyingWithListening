package com.ssafy.domain.sentence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.domain.sentence.model.entity.LectureSentence;

public interface LectureSentenceRepository extends JpaRepository<LectureSentence, Long> {

	@Query(value = "SELECT * FROM lecture_sentences WHERE lecture_id = :lectureId", nativeQuery = true)
	List<LectureSentence> findByLectureId(Long lectureId);

}
