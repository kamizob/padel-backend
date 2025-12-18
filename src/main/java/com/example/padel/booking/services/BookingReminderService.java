package com.example.padel.booking.services;

import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.authorization.services.EmailService;
import com.example.padel.booking.domain.Booking;
import com.example.padel.booking.repository.BookingDAO;
import com.example.padel.court.repository.CourtDAO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingReminderService {
    private final BookingDAO bookingDAO;
    private final AuthDAO authDAO;
    private final CourtDAO courtDAO;
    private final EmailService emailService;

    public BookingReminderService(BookingDAO bookingDAO, AuthDAO authDAO,
                                  CourtDAO courtDAO, EmailService emailService) {
        this.bookingDAO = bookingDAO;
        this.authDAO = authDAO;
        this.courtDAO = courtDAO;
        this.emailService = emailService;

    }

    @Scheduled(fixedRate = 1800000)
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeHoursLater = now.plusHours(24);
        List<Booking> upcoming = bookingDAO.findUpcomingActiveBookings(now, threeHoursLater);
        for (Booking booking : upcoming) {
            var user = authDAO.findByEmail(getUserEmailById(booking.userId()));
            var court = courtDAO.findCourtById(booking.courtId());
            String formattedTime = booking.startTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            if (user != null && court != null) {
                String subject = "Padelio rezervacijos priminimas";
                String message = "Sveiki, " + user.getFirstName() + "!\n\n" +
                        "Primename, kad jūsų rezervacija aikštelei \"" + court.name() + "\" prasidės " +
                        formattedTime + ".\n\nIki pasimatymo!\nPadelio komanda.";
                emailService.sendCustomEmail(user.getEmail(), subject, message);
                bookingDAO.markReminderSent(booking.id());
            }
        }

    }

    private String getUserEmailById(String userId) {
        return authDAO.findById(userId).getEmail();
    }
}
