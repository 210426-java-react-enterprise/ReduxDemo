package com.revature.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String s) {
        super(s);
    }

    public AuthorizationException() {
        super("A forbidden request was made.");
    }
}
