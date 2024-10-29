package com.ssafy.domain.koala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.domain.koala.model.entity.Koala;
import com.ssafy.domain.user.model.entity.User;

public interface KoalaRepository extends JpaRepository<Koala, Long> {

	@Query("SELECT k FROM Koala k WHERE k.user = :user")
	Koala findByUser(@Param("user") User user);

}
