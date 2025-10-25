package com.example.padel.court.api.controller;

import com.example.padel.court.domain.Court;
import com.example.padel.court.services.CourtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }
    @GetMapping
    public List<Court> getAllActiveCourts() {
        return courtService.getAllActiveCourts();

    }
}
