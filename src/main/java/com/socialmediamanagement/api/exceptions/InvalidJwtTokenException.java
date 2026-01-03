package com.socialmediamanagement.api.exceptions;

@SuppressWarnings("serial")
public class InvalidJwtTokenException extends RuntimeException {

	  public InvalidJwtTokenException(String message) {
	        super(message);
	    }
}
