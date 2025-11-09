package com.example.padel.exception.custom;

public class FailedCreateBookingException extends RuntimeException {
    public FailedCreateBookingException(String message) {
        super(message);
    }
}
