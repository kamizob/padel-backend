package com.example.padel.schedule.api.response;

import java.util.List;

public record ScheduleResponse(
        String courtId,
        String courtName,
        List<String> slots
) {
}
