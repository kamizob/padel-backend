package com.example.padel.court.services;

import com.example.padel.court.api.request.CreateCourtRequest;
import com.example.padel.court.api.response.CreateCourtResponse;
import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.FailedCreateCourtException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCourtService {
    private static final int SUCCESS = 1;
    private final CourtDAO courtDAO;

    public CreateCourtService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }
    public CreateCourtResponse createCourt(CreateCourtRequest request) {
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
}
