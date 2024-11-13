package com.ssafy.a304.shortgong.domain.sentence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.SentenceTitle;

public interface SentenceTitleRepository extends JpaRepository<SentenceTitle, Long> {
	
}
