package com.example.padel.authorization.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Padel Account Verification");
        message.setText("Sveiki, \n\nNorėdami aktyvuoti savo paskyrą rezervacijų kūrimui, paspauskite šią nuorodą:\n\n"
        + verificationLink + "\n\nDėkojame, \nPadelio komanda");
        message.setFrom("respadel.app@gmail.com");
        mailSender.send(message);

    }
}
