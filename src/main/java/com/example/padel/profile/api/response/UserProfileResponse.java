package com.example.padel.profile.api.response;

public record UserProfileResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String role,
        boolean isVerified
) {
}
