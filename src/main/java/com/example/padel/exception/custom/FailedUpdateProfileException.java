package com.example.padel.exception.custom;

public class FailedUpdateProfileException extends RuntimeException {
    public FailedUpdateProfileException(String message) {
        super(message);
    }
}
