package com.example.padel.profile.api.request;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(max = 100, message = "First name is too long")
        String firstName,

        @Size(max = 100, message = "Last name is too long")
        String lastName,

        @Size(min = 6, max = 60, message = "Password must be 6–60 characters long")
        String newPassword
) {
}
