package com.example.padel.authorization.api.request;

public record SignUpRequest(String email, String password, String firstName, String lastName) {
}
