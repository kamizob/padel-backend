package com.example.padel.authorization.services;

import com.example.padel.authorization.api.request.LoginRequest;
import com.example.padel.authorization.api.response.LoginResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.config.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthDAO authDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(AuthDAO authDAO, PasswordEncoder passwordEncoder,  JwtService jwtService) {
        this.authDAO = authDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public LoginResponse login(LoginRequest request) {
        User user = authDAO.findByEmail(request.email());
        if (user == null) {
            throw new RuntimeException("User nor found with email: " + request.email());
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token);
    }
}
