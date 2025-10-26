package com.example.padel.court.api.controller;

import com.example.padel.court.api.request.CreateCourtRequest;
import com.example.padel.court.api.request.UpdateCourtRequest;
import com.example.padel.court.api.response.CreateCourtResponse;
import com.example.padel.court.api.response.UpdateCourtResponse;
import com.example.padel.court.domain.Court;
import com.example.padel.court.services.CourtService;
import com.example.padel.court.services.CreateCourtService;
import com.example.padel.court.services.UpdateCourtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final UpdateCourtService updateCourtService;

    public CourtController(CourtService courtService,  CreateCourtService createCourtService,
                           UpdateCourtService updateCourtService) {
        this.courtService = courtService;
        this.createCourtService = createCourtService;
        this.updateCourtService = updateCourtService;
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

    @PatchMapping("/{courtId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UpdateCourtResponse updateCourtActivity(
            @PathVariable String courtId,
            @RequestBody UpdateCourtRequest request
    ) {
        return updateCourtService.updateCourtActivity(courtId, request);
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Court> getAllCourts() {
        return courtService.getAllCourts();
    }




}
