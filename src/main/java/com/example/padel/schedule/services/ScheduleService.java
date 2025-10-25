package com.example.padel.schedule.services;

import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.CourtNotFoundException;
import com.example.padel.schedule.api.response.ScheduleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final CourtDAO courtDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public ScheduleService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }
    public ScheduleResponse getCourtSchedule(String id) {
        Court court = courtDAO.findCourtById(id);
        if (court == null) {
            throw new CourtNotFoundException("Court not found with id " + id);
        }
        List <String> slots = generateSlots(court.openingTime(), court.closingTime(), court.slotMinutes());
        return new ScheduleResponse(court.id(), court.name(), slots);

    }
    private List<String> generateSlots(LocalTime open, LocalTime close, int slotMinutes) {
        List<String> slots = new ArrayList<>();
        LocalTime current = open;
        while (current.plusMinutes(slotMinutes).isBefore(close) || current.plusMinutes(slotMinutes).equals(close)) {
            String slot = current.format(FORMATTER) + " - " + current.plusMinutes(slotMinutes).format(FORMATTER);
            slots.add(slot);
            current = current.plusMinutes(slotMinutes);
        }
        return slots;
    }
}
