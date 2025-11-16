package com.example.padel.booking.services;

import com.example.padel.booking.api.response.CancelBookingResponse;
import com.example.padel.booking.repository.BookingDAO;
import com.example.padel.config.JwtService;
import com.example.padel.exception.custom.FailedCancelBookingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CancelBookingService {
    private static final int SUCCESS = 1;
    private final BookingDAO bookingDAO;
    private final JwtService jwtService;

    public CancelBookingService(BookingDAO bookingDAO, JwtService jwtService) {
        this.bookingDAO = bookingDAO;
        this.jwtService = jwtService;
    }
    public CancelBookingResponse cancelBooking (String bookingId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Authorization header");
        }
        String userId = jwtService.extractUserId(token.substring(7));
        int result = bookingDAO.cancelBooking(bookingId, userId);
        if (result != SUCCESS) {
            throw new FailedCancelBookingException("Failed to cancel booking");

        }
        return new CancelBookingResponse("Booking cancelled successfully!");
    }
}
