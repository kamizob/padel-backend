package com.example.padel.profile.services;

import com.example.padel.config.JwtService;
import com.example.padel.exception.custom.UserNotFoundException;
import com.example.padel.profile.api.response.UserProfileResponse;
import com.example.padel.profile.repository.UserProfileDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class GetProfileService {
    private final UserProfileDAO userProfileDAO;
    private final JwtService jwtService;

    public GetProfileService(UserProfileDAO userProfileDAO, JwtService jwtService) {
        this.userProfileDAO = userProfileDAO;
        this.jwtService = jwtService;
    }

    public UserProfileResponse getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Authorization header");
        }

        String userId = jwtService.extractUserId(token.substring(7));
        if (userId == null) {
            throw new UserNotFoundException("User not authenticated");
        }

        UserProfileResponse user = userProfileDAO.findProfileById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }
}
