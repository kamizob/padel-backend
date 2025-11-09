package com.example.padel.booking.api.controller;

import com.example.padel.booking.api.request.CreateBookingRequest;
import com.example.padel.booking.api.response.CreateBookingResponse;
import com.example.padel.booking.services.CreateBookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final CreateBookingService createBookingService;

    public BookingController(CreateBookingService createBookingService) {
        this.createBookingService = createBookingService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CreateBookingResponse createBooking(
            HttpServletRequest request,
            @RequestBody CreateBookingRequest bookingRequest
    ) {
        return createBookingService.createBooking(request, bookingRequest);
    }

}
