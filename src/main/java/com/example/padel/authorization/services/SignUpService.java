package com.example.padel.authorization.services;

import com.example.padel.authorization.api.request.SignUpRequest;
import com.example.padel.authorization.api.response.SignUpResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.domain.enums.Role;
import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.config.VerificationTokenService;
import com.example.padel.exception.custom.EmailAlreadyExistsException;
import com.example.padel.exception.custom.SignUpFailedException;
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

    public SignUpResponse createUser(SignUpRequest signUpRequest, Role role, boolean isVerified, String successMsg) {
        String userId = UUID.randomUUID().toString();
        User existing = authDAO.findByEmail(signUpRequest.email());
        if (existing != null) {
            throw new EmailAlreadyExistsException("User with this email already exists!");
        }
        String password = passwordEncoder.encode(signUpRequest.password());

        User user = new User(
                userId,
                signUpRequest.email(),
                password,
                signUpRequest.firstName(),
                signUpRequest.lastName(),
                role,
                isVerified
        );
        int result = authDAO.signUp(user);
        if (result != SUCCESS) {
            throw new SignUpFailedException("Sign up failed. Please try again later.");
        }
        if(!isVerified) {
            String token = verificationTokenService.generateVerificationToken(user.getEmail());
            String link = "http://localhost:8080/api/auth/verify?token=" + token;
            emailService.sendVerificationEmail(user.getEmail(), link);
        }

        return new SignUpResponse(successMsg);
    }
    public SignUpResponse signUp(SignUpRequest r) {
        return createUser(r, Role.USER, false, "Sign up successful! Please verify your email.");
    }

    public SignUpResponse signUpAdmin(SignUpRequest r) {
        return createUser(r, Role.ADMIN, true, "Admin account created successfully!");
    }

}
