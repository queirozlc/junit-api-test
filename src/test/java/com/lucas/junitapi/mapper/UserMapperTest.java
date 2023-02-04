package com.lucas.junitapi.mapper;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.request.UserRequestBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserMapperTest {
	public static final String NAME = "user";
	public static final String EMAIL = "user@email.com";
	public static final String PASSWORD = "user";
	public static UserRequestBody userRequestBody;
	public static User user;

	@MockBean
	private UserMapper mapper;

	@BeforeEach
	void setup(){
		initUsers();
	}

	@Test
	void toUser() {
		Mockito.when(mapper.toUser(Mockito.any(UserRequestBody.class))).thenReturn(user);
		User response = mapper.toUser(userRequestBody);
		Assertions.assertThat(response).isInstanceOf(User.class);
		Assertions.assertThat(response.getEmail()).isEqualTo(userRequestBody.getEmail());
		Assertions.assertThat(response.getPassword()).isEqualTo(userRequestBody.getPassword());
		Assertions.assertThat(response.getName()).isEqualTo(userRequestBody.getName());
	}

	private void initUsers() {
		user = User.builder()
				.password(PASSWORD)
				.email(EMAIL)
				.name(NAME)
				.build();
		userRequestBody = new UserRequestBody(NAME, EMAIL, PASSWORD);
	}
}