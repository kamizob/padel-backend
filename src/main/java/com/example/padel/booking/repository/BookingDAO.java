package com.example.padel.booking.repository;

import com.example.padel.booking.domain.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDAO {
    int create(Booking booking);

    List<Booking> findByUserId(String userId);

    List<Booking> findByCourtId(String courtId);

    int cancelBooking(String bookingId, String userId);

    List<Booking> findUpcomingActiveBookings(LocalDateTime now, LocalDateTime oneHourLater);

    int markReminderSent(String bookingId);

    List<Booking> findByUserIdPaged(String userId, int offset, int limit);

    long countByUserId(String userId);

}
