package com.lucas.junitapi.controller;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.request.UserRequestBody;
import com.lucas.junitapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAllUsers() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable UUID id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody @Valid UserRequestBody userRequestBody) {
		return new ResponseEntity<>(service.createUser(userRequestBody), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody @Valid UserRequestBody userRequestBody,
	                                       @PathVariable UUID id) {
		return ResponseEntity.ok(service.updateUser(userRequestBody, id));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
		service.deleteUserById(id);
		return ResponseEntity.ok("User deleted with success");
	}
}
