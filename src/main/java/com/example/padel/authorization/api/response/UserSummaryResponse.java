package com.example.padel.authorization.api.response;

public record UserSummaryResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String role,
        boolean isVerified
) {
}
