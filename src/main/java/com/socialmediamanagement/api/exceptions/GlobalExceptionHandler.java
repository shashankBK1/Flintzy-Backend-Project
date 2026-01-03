package com.socialmediamanagement.api.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.socialmediamanagement.api.utility.CustomErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<CustomErrorResponse> handleUnauthorized(UnauthorizedException exception) {
		return new ResponseEntity<CustomErrorResponse>(
				new CustomErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now()),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleNotFound(ResourceNotFoundException exception) {
		return new ResponseEntity<CustomErrorResponse>(
				new CustomErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> globalHandler(Exception exception) {
		return new ResponseEntity<CustomErrorResponse>(new CustomErrorResponse("INTERNAL SERVER ERROR",
				HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<CustomErrorResponse> handleExternalApi(HttpClientErrorException ex) {

		return new ResponseEntity<>(
				new CustomErrorResponse("Invalid facebook token", ex.getStatusCode().value(), LocalDateTime.now()),
				ex.getStatusCode());
	}

	@ExceptionHandler(FacebookPageNotLinkedException.class)
	public ResponseEntity<CustomErrorResponse> handleFacebookPageNotLinkedException(FacebookPageNotLinkedException ex) {

		return new ResponseEntity<>(
				new CustomErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<CustomErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return new ResponseEntity<>(
				new CustomErrorResponse("Invalid parameter type", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String messgae = error.getDefaultMessage();
			errors.put(fieldName, messgae);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
