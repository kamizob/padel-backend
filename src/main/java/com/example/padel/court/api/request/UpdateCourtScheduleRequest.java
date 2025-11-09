package com.example.padel.court.api.request;

import java.time.LocalTime;

public record UpdateCourtScheduleRequest(
        LocalTime openTime,
        LocalTime closeTime,
        int slotMinutes
) {
}
