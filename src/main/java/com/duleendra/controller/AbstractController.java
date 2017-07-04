package com.duleendra.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.duleendra.exception.AppServiceException;

/**
 * Exceptions in controller functions are handled here
 *
 *
 * @author Duleendra Shashimal
 *
 */

public abstract class AbstractController {

    @Value("${message.internal.server.error}")
    private String internalServerError;

	@ExceptionHandler(AppServiceException.class)
	public ResponseEntity<Object> errorHandler(AppServiceException e) {
		return new ResponseEntity<Object>(internalServerError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
