package com.ssafy.a304.shortgong.domain.sentence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {

}
