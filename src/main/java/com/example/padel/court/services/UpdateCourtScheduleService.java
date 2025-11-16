package com.example.padel.court.services;

import com.example.padel.court.api.request.UpdateCourtScheduleRequest;
import com.example.padel.court.api.response.UpdateCourtScheduleResponse;
import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.court.util.CourtValidationUtil;
import com.example.padel.exception.custom.CourtNotFoundException;
import com.example.padel.exception.custom.FailedUpdateCourtException;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourtScheduleService {
    private static final int SUCCESS = 1;
    private final CourtDAO courtDAO;

    public UpdateCourtScheduleService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }

    public UpdateCourtScheduleResponse updateCourtSchedule(String courtId, UpdateCourtScheduleRequest request) {
        Court court = courtDAO.findCourtById(courtId);
        if (court == null) {
            throw new CourtNotFoundException("Court not found with id: " + courtId);
        }
        CourtValidationUtil.validateCourtTimes(
                request.openTime(),
                request.closeTime(),
                request.slotMinutes());


        int result = courtDAO.updateCourtSchedule(courtId, request.openTime(), request.closeTime(), request.slotMinutes());
        if (result != SUCCESS) {
            throw new FailedUpdateCourtException("Failed to update court schedule");
        }

        return new UpdateCourtScheduleResponse(
                courtId,
                court.name(),
                request.openTime(),
                request.closeTime(),
                request.slotMinutes(),
                "Court schedule updated successfully"
        );
    }

}
