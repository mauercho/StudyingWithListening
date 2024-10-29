package com.ssafy.domain.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.domain.user.model.entity.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

	Ranking findByUserId(Long userId);

	List<Ranking> findTop10ByOrderByRanking();

	@Modifying
	@Query("DELETE FROM Ranking")
	void clearRankingTable();

	@Query("SELECT COUNT(r) + 1 FROM Ranking r WHERE r.user.userLevel > :userLevel " +
		"OR (r.user.userLevel = :userLevel AND r.user.userExp > :userExp)")
	int findNewUserRank(@Param("userLevel") int userLevel, @Param("userExp") int userExp);

}
