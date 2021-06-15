package com.revature.exceptions;

public class UsernameUnavailibleException extends RuntimeException{
    public UsernameUnavailibleException() {
        super("username taken");
    }
}
