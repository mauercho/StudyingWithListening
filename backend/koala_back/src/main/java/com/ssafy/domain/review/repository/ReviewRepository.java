package com.ssafy.domain.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.domain.review.model.entity.ReviewSentence;
import com.ssafy.domain.sentence.model.entity.Sentence;
import com.ssafy.domain.user.model.entity.User;

public interface ReviewRepository extends JpaRepository<ReviewSentence, Long> {

	List<ReviewSentence> findAllByUser(User user);

	@Query("select rs from ReviewSentence rs where rs.user = :user and "
		+ "rs.sentence.sentenceText like %:keyword%")
	List<ReviewSentence> findAllByKeywordAndUser(@Param("user") User user,
		@Param("keyword") String keyword);

	@Query("select rs from ReviewSentence rs where rs.user = :user and "
		+ "rs.sentence.topicCategory = :topic")
	List<ReviewSentence> findAllByTopicAndUser(@Param("user") User user,
		@Param("topic") String topic);

	@Query("select rs from ReviewSentence rs where rs.user = :user and "
		+ "rs.sentence.sentenceText like %:keyword% and rs.sentence.topicCategory = :topic")
	List<ReviewSentence> findAllByKeywordAndTopicAndUser(@Param("user") User user,
		@Param("keyword") String keyword, @Param("topic") String topic);

	boolean existsByUserAndSentence(User user, Sentence sentence);

}
