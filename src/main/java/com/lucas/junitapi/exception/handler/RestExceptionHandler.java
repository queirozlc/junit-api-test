package com.lucas.junitapi.exception.handler;

import com.lucas.junitapi.exception.bad_request.BadRequestException;
import com.lucas.junitapi.exception.bad_request.BadRequestExceptionDetails;
import com.lucas.junitapi.exception.validation.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		String fields = fieldErrors
				.stream()
				.map(FieldError::getField)
				.collect(Collectors.joining(", "));
		String messages = fieldErrors
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(", "));

		ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails
				.builder()
				.title("Invalid Fields. Check api documentation")
				.details(ex.getClass().getName())
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.fields(fields)
				.message(messages)
				.build();

		return ResponseEntity.badRequest().body(validationExceptionDetails);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException ex) {
		BadRequestExceptionDetails badRequestExceptionDetails = BadRequestExceptionDetails
				.builder()
				.details(ex.getClass().getName())
				.message(ex.getMessage())
				.title("Bad Request Exception. Check api documentation.")
				.status(HttpStatus.BAD_REQUEST.value())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.badRequest().body(badRequestExceptionDetails);
	}
}
