package com.ssafy.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.domain.user.model.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {

	Auth findByAuthName(String authName);

}
