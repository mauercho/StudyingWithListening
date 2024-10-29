package com.ssafy.domain.user.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.user.model.entity.Ranking;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.RankingRepository;
import com.ssafy.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingSchedulerServiceImpl implements RankingSchedulerService {

	private final UserRepository userRepository;
	private final RankingRepository rankingRepository;

	@Override
	@Transactional
	@Scheduled(cron = "0 0 * * * *")
	public void rankingScheduler() {
		rankingRepository.clearRankingTable();

		List<User> users = userRepository.findAll(Sort.by(Sort.Order.desc("userLevel"), Sort.Order.desc("userExp")));

		int currentRank = 1;
		int previousRank = 1;
		User previousUser = null;

		for (User user : users) {
			if (previousUser != null && user.getUserLevel().equals(previousUser.getUserLevel()) && user.getUserExp()
				.equals(previousUser.getUserExp())) {
				currentRank = previousRank;
			} else {
				previousRank = currentRank;
			}

			Ranking ranking = Ranking.builder()
				.user(user)
				.ranking(currentRank)
				.build();

			rankingRepository.save(ranking);
			currentRank++;
			previousUser = user;
		}

	}
}
