package com.lucas.junitapi.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
public abstract class ExceptionDetails {
	private String title;
	private String details;
	private String message;
	private LocalDateTime timestamp;
	private int status;
}
