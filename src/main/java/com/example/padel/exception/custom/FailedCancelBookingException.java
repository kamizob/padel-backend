package com.example.padel.exception.custom;

public class FailedCancelBookingException extends RuntimeException {
    public FailedCancelBookingException(String message) {
        super(message);
    }
}
