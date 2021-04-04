package com.dev.oms.errors;

public class ConflictReviewException extends RuntimeException {

    public ConflictReviewException(String message) {
        super(message);
    }

    public ConflictReviewException(String message, Throwable cause) {
        super(message, cause);
    }
}
