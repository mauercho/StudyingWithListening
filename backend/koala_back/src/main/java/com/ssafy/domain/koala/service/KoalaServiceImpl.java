package com.ssafy.domain.koala.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.domain.koala.model.dto.request.KoalaNameRequest;
import com.ssafy.domain.koala.model.dto.response.KoalaResponse;
import com.ssafy.domain.koala.model.entity.Koala;
import com.ssafy.domain.koala.repository.KoalaRepository;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.UserRepository;
import com.ssafy.global.common.UserInfoProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KoalaServiceImpl implements KoalaService {

	private final UserInfoProvider userInfoProvider;
	private final UserRepository userRepository;
	private final KoalaRepository koalaRepository;

	@Override
	public KoalaResponse getKoala() {
		return KoalaResponse.toDto(koalaRepository.findByUser(userInfoProvider.getCurrentUser()));
	}

	@Override
	@Transactional
	public KoalaResponse changeKoalaName(KoalaNameRequest koalaNameRequest, Long koalaId) {
		User user = userInfoProvider.getCurrentUser();
		Koala koala = koalaRepository.findById(koalaId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 코알라 정보가 존재하지 않습니다."));
		if (!koala.getUser().equals(user)) {
			throw new IllegalArgumentException("해당 유저의 코알라 정보와 일치하지 않습니다.");
		}

		koala.updateKoalaName(koalaNameRequest.getKoalaName());
		return KoalaResponse.toDto(koalaRepository.save(koala));
	}

	@Override
	@Transactional
	public KoalaResponse increaseKoalaExp(Long koalaId) {
		User user = userInfoProvider.getCurrentUser();
		Koala koala = koalaRepository.findById(koalaId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 코알라 정보가 존재하지 않습니다."));
		if (!koala.getUser().equals(user)) {
			throw new IllegalArgumentException("해당 유저의 코알라 정보와 일치하지 않습니다.");
		}

		user.decreaseLeaves();
		koala.increaseKoalaExp();

		return KoalaResponse.toDto(koalaRepository.save(koala));
	}

	@Override
	@Transactional
	public void addKoala(Long userId) {
		koalaRepository.save(Koala.builder().user(userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 유저 정보가 존재하지 않습니다."))).build());
	}
}
