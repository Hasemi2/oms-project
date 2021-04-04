package com.dev.oms.errors;

public class OrderStateNotAllowException extends RuntimeException {

    public OrderStateNotAllowException(String message) {
        super(message);
    }

    public OrderStateNotAllowException(String message, Throwable cause) {
        super(message, cause);
    }
}
