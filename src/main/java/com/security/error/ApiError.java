package com.security.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

	 private LocalDateTime timeStamp;
	 private String error;
	 private HttpStatus statusCode;
	 
	 
	 public ApiError(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	 }


	 public ApiError(String error, HttpStatus statusCode) {
		this.error = error;
		this.statusCode = statusCode;
	 }
	
	 
	 
}
