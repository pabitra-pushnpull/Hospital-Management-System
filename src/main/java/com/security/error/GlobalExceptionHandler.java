package com.security.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException exception) {
		
		ApiError apiError = new ApiError("Username not found with username : " + exception.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError, apiError.getStatusCode());
	}
	
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception) {
		
		ApiError apiError = new ApiError("Authentication failed : " + exception.getMessage(), HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED);
		
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ApiError> handleJwtException(JwtException exception) {
		
		ApiError apiError = new ApiError("Invalid JWT token : " + exception.getMessage(), HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED);
		
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException exception) {
		
		ApiError apiError = new ApiError("Access Denied : Insufficient permissions", HttpStatus.FORBIDDEN);
		return new ResponseEntity<ApiError>(apiError, HttpStatus.FORBIDDEN);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGenericException(Exception exception) {
		
		ApiError apiError = new ApiError("An unexpected error occured : " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
}
