package com.lucas.junitapi.service.impl;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.exception.bad_request.BadRequestException;
import com.lucas.junitapi.mapper.UserMapper;
import com.lucas.junitapi.repository.UserRepository;
import com.lucas.junitapi.request.UserRequestBody;
import com.lucas.junitapi.service.UserService;
import jakarta.validation.Valid;
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
	public User createUser(@Valid UserRequestBody userRequestBody) {
		User user = mapper.toUser(userRequestBody);
		if (repository.existsByEmail(user.getEmail()))
			throw new BadRequestException("Already exists a User with this email.");
		return repository.save(user);
	}

	@Override
	public User updateUser(@Valid UserRequestBody userRequestBody, UUID id) {
		User userToBeUpdated = repository.findById(id)
				.orElseThrow(() -> new BadRequestException("User not found"));
		User userToReplace = mapper.toUser(userRequestBody);

		if (!userToReplace.getEmail().equals(userToBeUpdated.getEmail()) &&
		repository.existsByEmail(userToReplace.getEmail())) {
			throw new BadRequestException("Already exists a user with this email.");
		}

		userToReplace.setId(userToBeUpdated.getId());
		return repository.save(userToReplace);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteUserById(UUID id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new BadRequestException("User not found."));
		repository.delete(user);
	}
}
