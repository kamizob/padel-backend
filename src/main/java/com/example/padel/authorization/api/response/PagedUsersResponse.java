package com.example.padel.authorization.api.response;

import java.util.List;

public record PagedUsersResponse(
        List<UserSummaryResponse> users,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
