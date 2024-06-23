package com.springbootproject.banking_application.exception;

public class AccountException extends RuntimeException{

    public AccountException(String message){
        super(message);
    }
}
