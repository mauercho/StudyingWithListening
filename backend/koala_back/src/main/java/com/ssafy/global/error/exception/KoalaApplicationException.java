package com.ssafy.global.error.exception;

public class KoalaApplicationException extends RuntimeException {

	public KoalaApplicationException() {
		super();
	}

	public KoalaApplicationException(final String message) {
		super(message);
	}

	public KoalaApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public KoalaApplicationException(final Throwable cause) {
		super(cause);
	}
}
