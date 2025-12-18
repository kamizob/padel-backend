package com.example.padel.authorization.services;

import com.example.padel.authorization.api.request.LoginRequest;
import com.example.padel.authorization.api.response.LoginResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.config.JwtService;
import com.example.padel.exception.custom.InvalidPasswordException;
import com.example.padel.exception.custom.UserNotFoundException;
import com.example.padel.exception.custom.UserNotVerifiedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthDAO authDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(AuthDAO authDAO, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authDAO = authDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = authDAO.findByEmail(request.email());
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + request.email());
        }
        if (!user.isVerified()) {
            throw new UserNotVerifiedException("Please verify your email before logging in!");
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new LoginResponse(token);
    }
}
