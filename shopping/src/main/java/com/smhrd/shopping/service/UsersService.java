package com.smhrd.shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.shopping.model.repository.UsersJpaRepository;

import com.smhrd.shopping.model.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {
	private final UsersJpaRepository usersJpaRepository;
	
	@Transactional
	public Long save() {
		log.info("usersService method save start...");
		Users users = usersJpaRepository.findByUsername("user");
		return (users == null) ? usersJpaRepository.save(Users.builder().build()).getId() : users.getId();
	}
}
