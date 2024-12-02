package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1932985660523377087L;
	private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
