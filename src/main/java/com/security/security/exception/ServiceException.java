package com.security.security.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Setter
@Getter
public class ServiceException extends RuntimeException{
        

	private static final long serialVersionUID = 1L;

	private final HttpStatus statusCode;

	private Map<String,Object> indexError;

	public ServiceException(String statusMessage ) {
		super(statusMessage);
		this.statusCode = HttpStatus.BAD_REQUEST;
	}

	public ServiceException(String statusMessage ,HttpStatus statusCode) {
		super(statusMessage);
		this.statusCode = statusCode;
	}

	public ServiceException(Map<String,Object> indexError ) {
		this.indexError=indexError;
		this.statusCode = HttpStatus.BAD_REQUEST;
	}
}
