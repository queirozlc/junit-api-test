package com.lucas.junitapi.repository;

import com.lucas.junitapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	boolean existsByEmail(String email);
}