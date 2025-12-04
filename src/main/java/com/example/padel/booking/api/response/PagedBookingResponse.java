package com.example.padel.booking.api.response;

import java.util.List;

public record PagedBookingResponse(
        List<MyBookingResponse> bookings,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
