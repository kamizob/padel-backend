package com.example.padel.schedule.api.controller;

import com.example.padel.schedule.api.response.ScheduleResponse;
import com.example.padel.schedule.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @GetMapping("/{courtId}")
    @ResponseStatus(HttpStatus.OK)
    public ScheduleResponse getCourtSchedule(@PathVariable String courtId) {
        return scheduleService.getCourtSchedule(courtId);
    }
}
