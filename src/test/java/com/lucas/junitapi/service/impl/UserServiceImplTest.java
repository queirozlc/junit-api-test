package com.lucas.junitapi.service.impl;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.exception.bad_request.BadRequestException;
import com.lucas.junitapi.mapper.UserMapper;
import com.lucas.junitapi.repository.UserRepository;
import com.lucas.junitapi.request.UserRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

	public static final UUID ID = UUID.fromString("1919f557-4381-4c7c-823b-fdc16a862a7c");
	public static final String NAME = "user";
	public static final String EMAIL = "user@email.com";
	public static final String PASSWORD = "user";
	public static final String USER_NOT_FOUND = "User not found.";
	public static User user;
	public static UserRequestBody dto;
	public static Optional<User> userOptional;

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserRepository repository;

	@Mock
	private UserMapper mapper;

	@BeforeEach
	void setUp() {
		initUsers();
	}

	@Test
	@DisplayName("when find by id is successful then returns an user.")
	void findByIdReturnsUserWhenSucessful() {
		when(repository.findById(any(UUID.class))).thenReturn(userOptional);

		User response = service.findById(ID);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@Test
	@DisplayName("when find by id is wrong, throw bad request exception")
	void findByIdThrowsBadRequestExceptionWhenUserNotFound() {
		when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

		try {
			service.findById(ID);
		}catch (Exception ex) {
			assertEquals(ex.getMessage(), USER_NOT_FOUND);;
		}
	}

	@Test
	void createUser() {
	}

	@Test
	void updateUser() {
	}

	@Test
	@DisplayName("find All users existent.")
	void findAll() {
	}

	@Test
	void deleteUserById() {
	}

	private void initUsers() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
		dto = new UserRequestBody(NAME, EMAIL, PASSWORD);
	}
}