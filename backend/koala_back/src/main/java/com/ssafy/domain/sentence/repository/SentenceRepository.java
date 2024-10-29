package com.ssafy.domain.sentence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.domain.sentence.model.entity.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {

	@Query(value = "SELECT sentence_id, users.user_id, sentence_text, topic_category, sentence_created_at, sentence_length FROM sentences JOIN users ON sentences.user_id = users.user_id WHERE topic_category = :topic AND users.auth_id = 0 ORDER BY RAND() LIMIT 10", nativeQuery = true)
	List<Sentence> findRandomSentencesByTopic(@Param("topic") String topic);

	@Query(value = "SELECT * FROM sentences WHERE user_id = :userId ORDER BY RAND() LIMIT 10", nativeQuery = true)
	List<Sentence> findRandomSentencesByUser(@Param("userId") Long userId);

	@Query(value = "SELECT sentence_id, users.user_id, sentence_text, topic_category, sentence_created_at, sentence_length FROM sentences JOIN users ON sentences.user_id = users.user_id WHERE users.auth_id = 0 OR users.user_id = :userId ORDER BY RAND() LIMIT 10", nativeQuery = true)
	List<Sentence> findRandomSentences(@Param("userId") Long userId);

}
