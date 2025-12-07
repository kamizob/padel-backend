package com.example.padel.court.api.controller;

import com.example.padel.court.api.request.CreateCourtRequest;
import com.example.padel.court.api.request.UpdateCourtRequest;
import com.example.padel.court.api.request.UpdateCourtScheduleRequest;
import com.example.padel.court.api.response.CreateCourtResponse;
import com.example.padel.court.api.response.UpdateCourtResponse;
import com.example.padel.court.api.response.UpdateCourtScheduleResponse;
import com.example.padel.court.domain.Court;
import com.example.padel.court.services.CourtService;
import com.example.padel.court.services.CreateCourtService;
import com.example.padel.court.services.UpdateCourtScheduleService;
import com.example.padel.court.services.UpdateCourtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    private final CourtService courtService;
    private final CreateCourtService createCourtService;
    private final UpdateCourtService updateCourtService;
    private final UpdateCourtScheduleService updateCourtScheduleService;

    public CourtController(CourtService courtService, CreateCourtService createCourtService,
                           UpdateCourtService updateCourtService,
                           UpdateCourtScheduleService updateCourtScheduleService) {
        this.courtService = courtService;
        this.createCourtService = createCourtService;
        this.updateCourtService = updateCourtService;
        this.updateCourtScheduleService = updateCourtScheduleService;
    }

    @GetMapping
    public List<Court> getAllActiveCourts() {
        return courtService.getAllActiveCourts();

    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourtResponse createCourt(@RequestBody CreateCourtRequest request) {
        return createCourtService.createCourt(request);
    }

    @PatchMapping("/{courtId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UpdateCourtResponse updateCourtActivity(
            @PathVariable String courtId,
            @RequestBody UpdateCourtRequest request
    ) {
        return updateCourtService.updateCourtActivity(courtId, request);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Court> getAllCourts() {
        return courtService.getAllCourts();
    }

    @PatchMapping("/{id}/schedule")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public UpdateCourtScheduleResponse updateCourtSchedule(
            @PathVariable String id,
            @RequestBody UpdateCourtScheduleRequest request
    ) {
        return updateCourtScheduleService.updateCourtSchedule(id, request);
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getPagedCourts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<Court> courts = courtService.getPagedCourts(page, size);
        int totalCourts = courtService.getTotalCourtCount();
        int totalPages = (int) Math.ceil((double) totalCourts / size);

        return Map.of(
                "courts", courts,
                "page", page,
                "size", size,
                "totalPages", totalPages,
                "totalCourts", totalCourts
        );
    }

    @GetMapping("/active/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getPagedActiveCourts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<Court> courts = courtService.getPagedActiveCourts(page, size);
        int totalCourts = courtService.getActiveCourtCount();
        int totalPages = (int) Math.ceil((double) totalCourts / size);

        return Map.of(
                "courts", courts,
                "page", page,
                "size", size,
                "totalPages", totalPages,
                "totalCourts", totalCourts
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Court getCourtById(@PathVariable String id) {
        return courtService.getCourtById(id);
    }


}
