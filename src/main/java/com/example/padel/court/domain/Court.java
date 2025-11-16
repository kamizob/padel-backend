package com.example.padel.court.domain;

import java.time.LocalTime;

public record Court(
        String id,
        String name,
        String location,
        boolean isActive,
        LocalTime openingTime,
        LocalTime closingTime,
        int slotMinutes
) {
}
