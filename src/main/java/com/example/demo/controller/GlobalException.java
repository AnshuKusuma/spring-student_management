package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.ExistsException;
import com.example.demo.exception.NotFoundException;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity notfound(NotFoundException ex) {
		return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ExistsException.class)
	public ResponseEntity exists(ExistsException ex) {
		return new ResponseEntity(ex.getMessage(),HttpStatus.ALREADY_REPORTED);
	}

}
