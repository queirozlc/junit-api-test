package com.lucas.junitapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequestBody {
	@NotBlank(message = "Name field cannot be empty.")
	private String name;
	@NotBlank(message = "E-mail field cannot be empty.")
	@Email(message = "Invalid email.")
	private String email;
	@NotBlank(message = "Password field cannot be empty.")
	private String password;
}
