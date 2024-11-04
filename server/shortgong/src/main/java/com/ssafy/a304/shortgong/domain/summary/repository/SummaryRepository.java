package com.ssafy.a304.shortgong.domain.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

public interface SummaryRepository extends JpaRepository<Summary, Long> {

}
