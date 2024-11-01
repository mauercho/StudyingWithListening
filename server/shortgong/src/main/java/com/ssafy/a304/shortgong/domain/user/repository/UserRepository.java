package com.ssafy.a304.shortgong.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a304.shortgong.domain.user.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
