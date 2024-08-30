package com.polezhaiev.banksystemapi.exception.app;

public class TransactionLackOfMoneyException extends RuntimeException {
    public TransactionLackOfMoneyException(String message) {
        super(message);
    }
}
