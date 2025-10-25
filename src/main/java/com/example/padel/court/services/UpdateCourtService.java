package com.example.padel.court.services;

import com.example.padel.court.api.request.UpdateCourtRequest;
import com.example.padel.court.api.response.UpdateCourtResponse;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.FailedUpdateCourtException;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourtService {
    private final static int SUCCESS = 1;
    private final CourtDAO courtDAO;

    public UpdateCourtService(CourtDAO courtDAO) {
    this.courtDAO = courtDAO;
    }

    public UpdateCourtResponse updateCourtActivity(String id, UpdateCourtRequest request) {
        boolean isActive = request.isActive();
        int result = courtDAO.updateCourtActivity(id, isActive);
        if (result != SUCCESS) {
            throw new FailedUpdateCourtException("Failed to update court activity with id " + id);
        }
        String message = isActive
                ? "Court activated successfully!"
                : "Court deactivated successfully!";
        return new UpdateCourtResponse(message);


    }
}
