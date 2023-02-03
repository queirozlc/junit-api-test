package com.lucas.junitapi.service;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.request.UserRequestBody;

import java.util.List;
import java.util.UUID;

public interface UserService {

	User findById(UUID id);

	User createUser(UserRequestBody userRequestBody);

	User updateUser(UserRequestBody userRequestBody, UUID id);

	List<User> findAll();

	void deleteUserById(UUID id);
}
