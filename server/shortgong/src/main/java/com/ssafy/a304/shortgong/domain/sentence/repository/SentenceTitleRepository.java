package com.ssafy.a304.shortgong.domain.sentence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.SentenceTitle;

public interface SentenceTitleRepository extends JpaRepository<SentenceTitle, Long> {

	boolean existsByName(String name);

	Optional<SentenceTitle> findByName(String title);
}
