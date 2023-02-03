package com.lucas.junitapi.repository;

import com.lucas.junitapi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@AutoConfigureDataJpa
class UserRepositoryTest {

	public static final UUID ID = UUID.fromString("1919f557-4381-4c7c-823b-fdc16a862a7c");
	static User user;
	static Optional<User> userOptional;

	@Autowired
	UserRepository repository;

	@BeforeEach
	void setup() {
		initUsers();
	}

	@Test
	@DisplayName("should find all users from database")
	void select_findAllUsersFromDatabase() {
		repository.save(user);

		List<User> listUsers = repository.findAll();

		assertEquals(listUsers.get(0).getClass(), User.class);
		assertNotNull(listUsers.get(0).getId());
		assertEquals(listUsers.size(), 1);
	}

	@Test
	@DisplayName("should find optional of user when find by id")
	void select_findOptionalOfUserWhenFindById() {
		user.setId(ID);
		User userSaved = repository.save(user);

		Optional<User> userFound = repository.findById(userSaved.getId());

		assertEquals(userFound.get().getClass(), User.class);
		assertEquals(userFound.get().getId(), userSaved.getId());
		assertNotNull(userFound.get());
		assertNotNull(userFound.get().getId());
	}

	@Test
	@DisplayName("save and persist a user when successful")
	void save_PersistUserWhenSuccessful() {
		user.setId(null);
		User userSaved = repository.save(user);

		assertEquals(userSaved.getClass(), User.class);
		assertNotNull(userSaved);
		assertNotNull(userSaved.getId());
		assertEquals(userSaved.getName(), user.getName());
		assertEquals(userSaved.getId(), user.getId());
	}

	@Test
	@DisplayName("return true if exists a user with existent email.")
	void verify_ReturnTrueIfExistsUserWithTheExistentEmail(){
		User userSaved = repository.save(user);
		var userToConsult = new User();
		userToConsult.setEmail(userSaved.getEmail());

		boolean existsByEmail = repository.existsByEmail(userToConsult.getEmail());

		assertTrue(existsByEmail);
	}

	private void initUsers() {
		user = User
				.builder()
				.name("user")
				.email("user@gmail.com")
				.password("user")
				.build();

		userOptional = Optional.of(User
				.builder()
				.name("user")
				.email("user@gmail.com")
				.password("user")
				.build());
	}
}