package com.commerce.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public String handleRTE(RuntimeException e) {
		return "Exception thrown : Inside Fallback Method";	
	}
	
}
