package com.example.padel.court.services;

import com.example.padel.court.api.request.CreateCourtRequest;
import com.example.padel.court.api.response.CreateCourtResponse;
import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.FailedCreateCourtException;
import com.example.padel.exception.custom.InvalidCourtConfigurationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class CreateCourtService {
    private static final int SUCCESS = 1;
    private final CourtDAO courtDAO;

    public CreateCourtService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }
    public CreateCourtResponse createCourt(CreateCourtRequest request) {
        validateCourtRequest(request);
        String courtId = UUID.randomUUID().toString();

        Court court = new Court(
                courtId,
                request.name(),
                request.location(),
                true,
                request.openTime(),
                request.closeTime(),
                request.slotMinutes()
        );
        int result = courtDAO.createCourt(court);
        if (result != SUCCESS) {
            throw new FailedCreateCourtException("Failed to create court");
        }
        return new CreateCourtResponse(courtId);

    }
    private void validateCourtRequest(CreateCourtRequest request) {
        LocalTime open = request.openTime();
        LocalTime close = request.closeTime();
        int slot =  request.slotMinutes();
        if (open == null || close == null) {
            throw new InvalidCourtConfigurationException("Opening and closing times can not be null");
        }
        if(!close.isAfter(open)) {
            throw new InvalidCourtConfigurationException("Closing time must be after opening time");
        }
        if (slot <= 0) {
            throw new InvalidCourtConfigurationException("Slot duration must be greater than 0 minutes");
        }
        long totalMinutes = Duration.between(open, close).toMinutes();
        if (slot > totalMinutes) {
            throw new InvalidCourtConfigurationException("Slot duration cannot be longer than working hours");
        }
        if (totalMinutes > 24 * 60) {
            throw new InvalidCourtConfigurationException("Working hours cannot exceed 24 hours");
        }

    }
}
