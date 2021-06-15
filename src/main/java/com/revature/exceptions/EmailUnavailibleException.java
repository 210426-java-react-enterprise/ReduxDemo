package com.revature.exceptions;

public class EmailUnavailibleException extends RuntimeException {
    public EmailUnavailibleException(){
        super("this email is already registered");
    }
}
