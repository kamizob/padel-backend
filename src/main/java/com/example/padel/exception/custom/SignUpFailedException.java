package com.example.padel.exception.custom;

public class SignUpFailedException extends RuntimeException {
    public SignUpFailedException(String message) {
        super(message);
    }
}
