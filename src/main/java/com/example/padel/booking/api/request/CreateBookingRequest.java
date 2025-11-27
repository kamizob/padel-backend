package com.example.padel.booking.api.request;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record CreateBookingRequest(
        String courtId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
