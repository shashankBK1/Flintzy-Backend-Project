package com.socialmediamanagement.api.exceptions;

@SuppressWarnings("serial")
public class FacebookPageNotLinkedException extends RuntimeException {

	public FacebookPageNotLinkedException(String message) {
		super(message);
	}
}
