package com.lucas.junitapi.service.impl;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.exception.bad_request.BadRequestException;
import com.lucas.junitapi.mapper.UserMapper;
import com.lucas.junitapi.repository.UserRepository;
import com.lucas.junitapi.request.UserPostRequestBody;
import com.lucas.junitapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository repository;
	private final UserMapper mapper;

	@Transactional(readOnly = true)
	@Override
	public User findById(UUID id) {
		return repository.findById(id).orElseThrow(() -> new BadRequestException("User not found."));
	}

	@Override
	public User createUser(UserPostRequestBody userPostRequestBody) {
		User user = mapper.toUser(userPostRequestBody);
		return repository.save(user);
	}

	@Override
	public User updateUser(UserPostRequestBody userPostRequestBody, UUID id) {
		return null;
	}
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}
}
