package com.ssafy.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.domain.user.model.entity.AiTalkLog;

public interface AiTalkLogRepository extends JpaRepository<AiTalkLog, Long> {

	Optional<AiTalkLog> findByUserId(Long userId);

}
