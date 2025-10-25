package com.example.padel.court.api.request;

import java.time.LocalTime;

public record CreateCourtRequest(
        String name,
        String location,
        LocalTime openTime,
        LocalTime closeTime,
        int slotMinutes
) {
}
