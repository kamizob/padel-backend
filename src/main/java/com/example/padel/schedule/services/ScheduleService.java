package com.example.padel.schedule.services;

import com.example.padel.booking.domain.Booking;
import com.example.padel.booking.repository.BookingDAO;
import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.CourtNotFoundException;
import com.example.padel.schedule.api.response.ScheduleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final CourtDAO courtDAO;
    private final BookingDAO bookingDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public ScheduleService(CourtDAO courtDAO, BookingDAO bookingDAO) {
        this.courtDAO = courtDAO;
        this.bookingDAO = bookingDAO;
    }
    public ScheduleResponse getCourtSchedule(String id, LocalDate date) {
        Court court = courtDAO.findCourtById(id);
        if (court == null) {
            throw new CourtNotFoundException("Court not found with id " + id);
        }
        List <String> allSlots = generateSlots(court.openingTime(), court.closingTime(), court.slotMinutes());

        List<Booking> bookings = bookingDAO.findByCourtId(court.id()).stream()
                .filter(Booking::isActive)
                .filter(b -> b.startTime().toLocalDate().equals(date))
                .toList();
        List<String> availableSlots = new ArrayList<>();
        for (String slot : allSlots) {
            String[] parts = slot.split(" - ");
            LocalDateTime slotStart = date.atTime(LocalTime.parse(parts[0], FORMATTER));
            LocalDateTime slotEnd = date.atTime(LocalTime.parse(parts[1], FORMATTER));

            boolean overlaps = bookings.stream().anyMatch(b ->
                    slotStart.isBefore(b.endTime()) && slotEnd.isAfter(b.startTime())
            );

            if (!overlaps) {
                availableSlots.add(slot);
            }
        }
        return new ScheduleResponse(court.id(), court.name(), availableSlots);

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
