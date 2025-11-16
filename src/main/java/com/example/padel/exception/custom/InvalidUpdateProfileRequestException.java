package com.example.padel.exception.custom;

public class InvalidUpdateProfileRequestException extends RuntimeException {
    public InvalidUpdateProfileRequestException(String message) {
        super(message);
    }
}
