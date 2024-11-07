package com.ssafy.a304.shortgong.domain.summary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;

public interface SummaryRepository extends JpaRepository<Summary, Long> {

	List<Summary> findAllByWriter(User user);
}
