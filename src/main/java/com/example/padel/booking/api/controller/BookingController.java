package com.example.padel.booking.api.controller;

import com.example.padel.booking.api.request.CreateBookingRequest;
import com.example.padel.booking.api.response.CancelBookingResponse;
import com.example.padel.booking.api.response.CreateBookingResponse;
import com.example.padel.booking.api.response.MyBookingResponse;
import com.example.padel.booking.api.response.PagedBookingResponse;
import com.example.padel.booking.services.BookingQueryService;
import com.example.padel.booking.services.CancelBookingService;
import com.example.padel.booking.services.CreateBookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final CreateBookingService createBookingService;
    private final BookingQueryService bookingQueryService;
    private final CancelBookingService cancelBookingService;

    public BookingController(CreateBookingService createBookingService, BookingQueryService bookingQueryService,
                             CancelBookingService cancelBookingService) {
        this.createBookingService = createBookingService;
        this.bookingQueryService = bookingQueryService;
        this.cancelBookingService = cancelBookingService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public CreateBookingResponse createBooking(
            HttpServletRequest request,
            @RequestBody CreateBookingRequest bookingRequest
    ) {
        return createBookingService.createBooking(request, bookingRequest);
    }
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public PagedBookingResponse getMyBookings(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookingQueryService.getBookingsForCurrentUser(request, page, size);
    }


    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public CancelBookingResponse cancelBooking(
            @PathVariable("id") String bookingId,
            HttpServletRequest request
    ) {
        return cancelBookingService.cancelBooking(bookingId, request);
    }


}
