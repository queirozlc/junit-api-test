package com.lucas.junitapi.exception.handler;

import com.lucas.junitapi.exception.bad_request.BadRequestException;
import com.lucas.junitapi.exception.bad_request.BadRequestExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException ex) {
		BadRequestExceptionDetails badRequestExceptionDetails = BadRequestExceptionDetails
				.builder()
				.details(ex.getClass().getName())
				.message(ex.getMessage())
				.title("Bad Request Exception. Check api documentation.")
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.badRequest().body(badRequestExceptionDetails);
	}
}
