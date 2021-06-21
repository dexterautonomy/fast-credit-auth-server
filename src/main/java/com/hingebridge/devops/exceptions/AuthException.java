package com.hingebridge.devops.exceptions;

public class AuthException extends RuntimeException{
	private static final long serialVersionUID = -920079447885155703L;
	private static final String MESSAGE = "Operation failed";

	public AuthException()
    {
        super(MESSAGE);
    }

    public AuthException(Throwable cause)
    {
        super(MESSAGE, cause);
    }

    public AuthException(String message)
    {
        super(message);
    }

    public AuthException(String message, Throwable cause)
    {
        super(message, cause);
    }
}