package com.example.padel.booking.api.controller;

import com.example.padel.booking.api.request.CreateBookingRequest;
import com.example.padel.booking.api.response.CreateBookingResponse;
import com.example.padel.booking.api.response.MyBookingResponse;
import com.example.padel.booking.services.BookingQueryService;
import com.example.padel.booking.services.CreateBookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final CreateBookingService createBookingService;
    private final BookingQueryService bookingQueryService;

    public BookingController(CreateBookingService createBookingService, BookingQueryService bookingQueryService) {
        this.createBookingService = createBookingService;
        this.bookingQueryService = bookingQueryService;
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
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<MyBookingResponse> getMyBookings(HttpServletRequest request) {
        return bookingQueryService.getBookingsForCurrentUser(request);
    }

}
