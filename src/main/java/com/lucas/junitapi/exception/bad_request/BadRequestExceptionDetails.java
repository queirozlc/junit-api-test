package com.lucas.junitapi.exception.bad_request;

import com.lucas.junitapi.exception.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BadRequestExceptionDetails extends ExceptionDetails {
}
