package com.example.padel.authorization.api.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRoleRequest(
        @NotBlank
        String userId,

        @NotBlank
        String newRole
) {
}
