package com.example.padel.booking.api.response;

import java.time.LocalDateTime;

public record MyBookingResponse(
        String id,
        String courtId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive
) {
}
