package com.example.padel.court.util;

import com.example.padel.exception.custom.InvalidCourtConfigurationException;

import java.time.Duration;
import java.time.LocalTime;

public class CourtValidationUtil {
    private CourtValidationUtil() {
    }

    public static void validateCourtTimes(LocalTime open, LocalTime close, int slotMinutes) {
        if (open == null || close == null) {
            throw new InvalidCourtConfigurationException("Opening and closing times cannot be null");
        }

        if (!close.isAfter(open)) {
            throw new InvalidCourtConfigurationException("Closing time must be after opening time");
        }

        if (slotMinutes <= 0) {
            throw new InvalidCourtConfigurationException("Slot duration must be greater than 0 minutes");
        }

        long totalMinutes = Duration.between(open, close).toMinutes();
        if (slotMinutes > totalMinutes) {
            throw new InvalidCourtConfigurationException("Slot duration cannot be longer than working hours");
        }

        if (totalMinutes > 24 * 60) {
            throw new InvalidCourtConfigurationException("Working hours cannot exceed 24 hours");
        }
    }
}
