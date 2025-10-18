package com.example.padel.authorization.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message="Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 60, message = "Password must be 6-60 characters long")
        String password,

        @NotBlank(message = "First name is required")
        @Size(max=100, message = "First name is too long")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max=100, message = "Last name is too long")
        String lastName
) {
}
