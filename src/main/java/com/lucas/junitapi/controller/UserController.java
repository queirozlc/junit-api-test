package com.lucas.junitapi.controller;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
