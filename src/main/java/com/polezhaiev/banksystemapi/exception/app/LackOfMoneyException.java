package com.polezhaiev.banksystemapi.exception.app;

public class LackOfMoneyException extends RuntimeException {
    public LackOfMoneyException(String message) {
        super(message);
    }
}
