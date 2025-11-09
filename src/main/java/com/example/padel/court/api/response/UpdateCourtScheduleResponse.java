package com.example.padel.court.api.response;

import java.time.LocalTime;

public record UpdateCourtScheduleResponse(
        String courtId,
        String courtName,
        LocalTime openTime,
        LocalTime closeTime,
        int slotMinutes,
        String message
) {
}
