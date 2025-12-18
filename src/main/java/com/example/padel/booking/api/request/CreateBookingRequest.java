package com.example.padel.booking.api.request;

import java.time.LocalDateTime;

public record CreateBookingRequest(
        String courtId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
