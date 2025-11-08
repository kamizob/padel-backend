package com.example.padel.booking.repository;

import com.example.padel.booking.domain.Booking;

import java.util.List;

public interface BookingDAO {
    int create(Booking booking);
    List<Booking> findByUserId(String userId);
    List<Booking> findByCourtId(String courtId);
}
