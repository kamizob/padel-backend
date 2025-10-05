package com.example.padel.authorization.services;

import com.example.padel.authorization.api.request.SignUpRequest;
import com.example.padel.authorization.api.response.SignUpResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.domain.enums.Role;
import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.config.VerificationTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignUpService {
    private static final int SUCCESS = 1;
    private final AuthDAO authDAO;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    public SignUpService(AuthDAO authDAO, PasswordEncoder passwordEncoder, EmailService emailService,
                         VerificationTokenService verificationTokenService) {

        this.authDAO = authDAO;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String userId = UUID.randomUUID().toString();
        User existing = authDAO.findByEmail(signUpRequest.email());
        if (existing != null) {
            return new SignUpResponse("User with this email already exists!");
        }
        String password = passwordEncoder.encode(signUpRequest.password());

        User user = new User(
                userId,
                signUpRequest.email(),
                password,
                signUpRequest.firstName(),
                signUpRequest.lastName(),
                Role.USER,
                false
        );
        int result = authDAO.signUp(user);
        if (result != SUCCESS) {
            return new SignUpResponse("Sign up failed");
        }
        String token = verificationTokenService.generateVerificationToken(user.getEmail());
        String link = "http://localhost:8080/api/auth/verify?token=" + token;

        emailService.sendVerificationEmail(user.getEmail(), link);
        return new SignUpResponse("Sign up successful! Please verify your email.");
    }
}
