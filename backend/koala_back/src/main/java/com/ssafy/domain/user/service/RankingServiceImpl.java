package com.ssafy.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.user.model.entity.Ranking;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.RankingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingServiceImpl implements RankingService {

	private final RankingRepository rankingRepository;

	@Override
	@Transactional
	public void createUserRanking(User user) {
		int userRank = rankingRepository.findNewUserRank(0, 0);
		rankingRepository.save(Ranking.builder()
			.user(user)
			.ranking(userRank)
			.build());
	}

}
