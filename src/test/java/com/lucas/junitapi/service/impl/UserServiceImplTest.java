package com.lucas.junitapi.service.impl;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.exception.bad_request.BadRequestException;
import com.lucas.junitapi.mapper.UserMapper;
import com.lucas.junitapi.repository.UserRepository;
import com.lucas.junitapi.request.UserRequestBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

	public static final UUID ID = UUID.fromString("1919f557-4381-4c7c-823b-fdc16a862a7c");
	public static final String NAME = "user";
	public static final String EMAIL = "user@email.com";
	public static final String PASSWORD = "user";
	public static final String USER_NOT_FOUND = "User not found.";
	public static User user;
	public static UserRequestBody userRequestBody;
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
		}
		catch (Exception ex) {
			assertEquals(USER_NOT_FOUND, ex.getMessage());

		}
	}

	@Test
	@DisplayName("save and create user when successful")
	void save_CreateUserWhenSuccessful() {
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(false);
		when(repository.save(any(User.class))).thenReturn(user);

		User response = service.createUser(userRequestBody);

		assertNotNull(response);
		assertNotNull(response.getId());
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		verify(repository, times(1)).save(user);
	}

	@Test
	@DisplayName("throw bad request when create user with existent email.")
	void save_ThrowBadRequestExceptionWhenCreateUserWithExistentEmail() {
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(true);

		try {
			service.createUser(userRequestBody);
		}
		catch (Exception ex) {
			assertEquals("Already exists a User with this email.", ex.getMessage());
			assertEquals(BadRequestException.class, ex.getClass());
		}
	}

	@Test
	@DisplayName("save and update a user when successful")
	void save_UpdateUserWhenSuccessful() {
		when(repository.findById(any(UUID.class))).thenReturn(userOptional);
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(false);
		when(repository.save(any(User.class))).thenReturn(user);
		user.setEmail("update@email.com");

		User response = service.updateUser(userRequestBody, ID);

		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals("update@email.com", response.getEmail());
		verify(repository, times(1)).save(user);
	}

	@Test
	@DisplayName("throw bad request when update user with email existent")
	void throw_BadRequestException_WhenUpdateUserWithExistentEmail() {
		when(repository.findById(any(UUID.class))).thenReturn(userOptional);
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(true);

		try {
			service.updateUser(userRequestBody, ID);
		}
		catch (Exception ex) {
			assertEquals(BadRequestException.class, ex.getClass());
			assertEquals("Already exists a user with this email.",
					ex.getMessage());
		}
	}

	@Test
	@DisplayName("find All users existent.")
	void select_FindAllUsersExistent_whenSuccessful() {
		when(repository.findAll()).thenReturn(List.of(user));
		List<User> users = service.findAll();
		assertEquals(User.class, users.get(0).getClass());
		assertEquals(1, users.size());
		assertEquals(ID, users.get(0).getId());
		assertEquals(EMAIL, users.get(0).getEmail());
	}

	@Test
	@DisplayName("return empty list when there's no users")
	void select_findAll_ReturnsEmptyList_WhenTheresNoUsers() {
		when(repository.findAll()).thenReturn(new ArrayList<>());

		List<User> users = service.findAll();

		assertEquals(0, users.size());
		Assertions.assertThat(users).isEmpty();
	}


	@Test
	@DisplayName("Removes user when find by id.")
	void deleteUserByIdWhenSuccessful() {
		when(repository.findById(any(UUID.class))).thenReturn(userOptional);

		service.deleteUserById(ID);

		verify(repository, times(1)).delete(any(User.class));
	}

	@Test
	@DisplayName("Throw bad request when not found a respective user to remove.")
	void delete_ThrowBadRequestExceptionWhenDeleteWhereNoUserWasFound() {
		when(repository.findById(any(UUID.class)))
				.thenThrow(new BadRequestException(USER_NOT_FOUND));

		try {
			service.deleteUserById(ID);
		}
		catch (Exception ex) {
			assertEquals(BadRequestException.class, ex.getClass());
			assertEquals(USER_NOT_FOUND, ex.getMessage());
		}
	}

	private void initUsers() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
		userRequestBody = new UserRequestBody(NAME, EMAIL, PASSWORD);
	}
}