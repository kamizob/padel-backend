package com.example.padel.booking.services;

import com.example.padel.booking.api.request.CreateBookingRequest;
import com.example.padel.booking.api.response.CreateBookingResponse;
import com.example.padel.booking.domain.Booking;
import com.example.padel.booking.repository.BookingDAO;
import com.example.padel.config.JwtService;
import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.CourtNotFoundException;
import com.example.padel.exception.custom.FailedCreateBookingException;
import com.example.padel.exception.custom.InvalidCourtConfigurationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CreateBookingService {
    private static final int SUCCESS = 1;

    private final BookingDAO bookingDAO;
    private final CourtDAO courtDAO;
    private final JwtService jwtService;

    public CreateBookingService(BookingDAO bookingDAO, CourtDAO courtDAO, JwtService jwtService) {
        this.bookingDAO = bookingDAO;
        this.courtDAO = courtDAO;
        this.jwtService = jwtService;
    }

    public CreateBookingResponse createBooking(HttpServletRequest request, CreateBookingRequest bookingRequest) {
        String token = request.getHeader("Authorization");
        String userId = jwtService.extractUserId(token.substring(7));
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        Court court = courtDAO.findCourtById(bookingRequest.courtId());
        if (court == null || !court.isActive()) {
            throw new CourtNotFoundException("Court not found with id: " + bookingRequest.courtId());
        }
        LocalDateTime startTime = bookingRequest.startTime();
        LocalDateTime endTime = bookingRequest.endTime();

        if(startTime == null || endTime == null || !endTime.isAfter(startTime)) {
            throw  new InvalidCourtConfigurationException("Start and end time are not valid");

        }
        List<Booking> existingBookings = bookingDAO.findByCourtId(bookingRequest.courtId());
        boolean overlaps = existingBookings.stream()
                .filter(Booking::isActive)
                .anyMatch(b -> startTime.isBefore(b.endTime()) && endTime.isAfter(b.startTime()));
        if (overlaps)
            throw new InvalidCourtConfigurationException("Selected time slot is already booked.");


        int startHour = startTime.getHour();
        int endHour = endTime.getHour();

        if(startHour < court.openingTime().getHour() || endHour > court.closingTime().getHour()) {
            throw  new InvalidCourtConfigurationException("Start and end time are not valid");
        }
        String bookingId = UUID.randomUUID().toString();

        Booking booking = new Booking(
                bookingId,
                userId,
                bookingRequest.courtId(),
                startTime,
                endTime,
                true
        );
        int result = bookingDAO.create(booking);
        if (result != SUCCESS) {
            throw new FailedCreateBookingException("Failed to create booking");

        }
        return new CreateBookingResponse(bookingId, "Booking created successfully");


    }

}
