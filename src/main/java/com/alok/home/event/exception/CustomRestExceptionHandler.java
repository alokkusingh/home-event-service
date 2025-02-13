package com.alok.home.event.exception;

import com.alok.home.commons.dto.exception.GlobalRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomRestExceptionHandler extends GlobalRestExceptionHandler {
}
