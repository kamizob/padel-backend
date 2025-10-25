package com.example.padel.court.api.controller;

import com.example.padel.court.api.request.CreateCourtRequest;
import com.example.padel.court.api.response.CreateCourtResponse;
import com.example.padel.court.domain.Court;
import com.example.padel.court.services.CourtService;
import com.example.padel.court.services.CreateCourtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    private final CourtService courtService;
    private final CreateCourtService createCourtService;

    public CourtController(CourtService courtService,  CreateCourtService createCourtService) {
        this.courtService = courtService;
        this.createCourtService = createCourtService;
    }
    @GetMapping
    public List<Court> getAllActiveCourts() {
        return courtService.getAllActiveCourts();

    }
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourtResponse createCourt(@RequestBody CreateCourtRequest request) {
        return createCourtService.createCourt(request);
    }


}
