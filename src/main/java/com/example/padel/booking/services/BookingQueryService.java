package com.example.padel.booking.services;

import com.example.padel.booking.api.response.MyBookingResponse;
import com.example.padel.booking.domain.Booking;
import com.example.padel.booking.repository.BookingDAO;
import com.example.padel.config.JwtService;
import com.example.padel.court.repository.CourtDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingQueryService {

    private final BookingDAO bookingDAO;
    private final CourtDAO courtDAO;
    private final JwtService jwtService;

    public BookingQueryService(BookingDAO bookingDAO, JwtService jwtService,
                               CourtDAO courtDAO) {
        this.bookingDAO = bookingDAO;
        this.courtDAO = courtDAO;
        this.jwtService = jwtService;
    }

    public List<MyBookingResponse> getBookingsForCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String userId = jwtService.extractUserId(token.substring(7));

        List<Booking> bookings = bookingDAO.findByUserId(userId);
        return bookings.stream()
                .map(b -> {
                    String courtName = "Unknown Court";
                    var court = courtDAO.findCourtById(b.courtId());
                    if (court != null) {
                        courtName = court.name();
                    }
                    return new MyBookingResponse(
                            b.id(),
                            b.courtId(),
                            courtName,
                            b.startTime(),
                            b.endTime(),
                            b.isActive()
                    );
                })
                .toList();
    }
}
