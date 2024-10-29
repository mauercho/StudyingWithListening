package com.ssafy.domain.ai_talk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.domain.ai_talk.model.entity.AiTalkSituation;

@Repository
public interface AiTalkRepository extends JpaRepository<AiTalkSituation, Long> {
	@Query(value = "SELECT * FROM ai_talk_situation WHERE topic_category = :topic", nativeQuery = true)
	List<AiTalkSituation> findByTopic(@Param("topic") String topic);
}
