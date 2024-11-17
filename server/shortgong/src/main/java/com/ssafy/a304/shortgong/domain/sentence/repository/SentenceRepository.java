package com.ssafy.a304.shortgong.domain.sentence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {

	List<Sentence> findAllBySummary_IdOrderByOrder(Long summaryId);

	/* order 벌크 업데이트 */
	@Modifying
	@Query("UPDATE Sentence s SET s.order = s.order + :increment WHERE s.summary.id = :summaryId AND s.order > :existingOrder")
	void bulkUpdateOrder(@Param("summaryId") Long summaryId, @Param("existingOrder") int existingOrder,
		@Param("increment") int increment);

	Optional<Sentence> findByQuestionAndSummary(String question, Summary summary);
}
