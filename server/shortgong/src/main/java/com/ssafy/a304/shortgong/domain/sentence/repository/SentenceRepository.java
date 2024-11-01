package com.ssafy.a304.shortgong.domain.sentence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {

	List<Sentence> findAllBySummary_Id(Long summaryId);

}
