package com.lucas.junitapi.service;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.request.UserPostRequestBody;

import java.util.List;
import java.util.UUID;

public interface UserService {

	User findById(UUID id);

	User createUser(UserPostRequestBody userPostRequestBody);

	User updateUser(UserPostRequestBody userPostRequestBody, UUID id);

	List<User> findAll();
}
