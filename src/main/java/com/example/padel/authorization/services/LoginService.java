package com.example.padel.authorization.services;

import com.example.padel.authorization.api.request.LoginRequest;
import com.example.padel.authorization.api.response.LoginResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.repository.AuthDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final AuthDAO authDAO;
    private final PasswordEncoder passwordEncoder;
    public LoginService(AuthDAO authDAO, PasswordEncoder passwordEncoder) {
        this.authDAO = authDAO;
        this.passwordEncoder = passwordEncoder;
    }
    public LoginResponse login(LoginRequest request) {
        User user = authDAO.findByEmail(request.email());
        if (user == null) {
            return new LoginResponse("User not found");
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return new LoginResponse("Invalid credentials");
        }
        return new LoginResponse("Logged in successfully");
    }
}
