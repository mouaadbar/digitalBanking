package com.example.benyamine.exceptions;

public class BankAccountNotFound extends Throwable{
    public BankAccountNotFound(String message) {
        super(message);
    }
}
