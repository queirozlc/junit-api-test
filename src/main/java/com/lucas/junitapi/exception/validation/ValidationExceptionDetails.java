package com.lucas.junitapi.exception.validation;

import com.lucas.junitapi.exception.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ValidationExceptionDetails extends ExceptionDetails {
	private String fields;
}
