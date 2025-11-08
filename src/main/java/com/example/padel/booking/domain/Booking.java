package com.example.padel.booking.domain;

import java.time.LocalDateTime;

public record Booking(
        String id,
        String userId,
        String courtId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive
) {
}
