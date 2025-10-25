package com.example.padel.exception.custom;

public class FailedCreateCourtException extends RuntimeException {
    public FailedCreateCourtException(String message) {
        super(message);
    }
}
