package com.example.padel.profile.services;

import com.example.padel.config.JwtService;
import com.example.padel.exception.custom.FailedUpdateProfileException;
import com.example.padel.exception.custom.InvalidUpdateProfileRequestException;
import com.example.padel.exception.custom.UserNotFoundException;
import com.example.padel.profile.api.request.UpdateProfileRequest;
import com.example.padel.profile.api.response.UpdateProfileResponse;
import com.example.padel.profile.repository.UserProfileDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateProfileService {
    private static final int SUCCESS = 1;
    private final UserProfileDAO userProfileDAO;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UpdateProfileService(UserProfileDAO userProfileDAO, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userProfileDAO = userProfileDAO;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UpdateProfileResponse updateProfile(UpdateProfileRequest request, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Authorization header");
        }

        String userId = jwtService.extractUserId(token.substring(7));
        if (userId == null) {
            throw new UserNotFoundException("User not authenticated");
        }

        if(request.firstName() == null && request.lastName() == null && request.newPassword() == null){
            throw new InvalidUpdateProfileRequestException("At least one of the fields is required");
        }

        String encodedPassword = null;
        if (request.newPassword() != null && !request.newPassword().isBlank()) {
            if (request.oldPassword() == null || request.oldPassword().isBlank()) {
                throw new InvalidUpdateProfileRequestException("Old password is required");
            }

            if (!passwordEncoder.matches(request.oldPassword(), userProfileDAO.findPasswordHashByUserId(userId))) {
                throw new InvalidUpdateProfileRequestException("Old password is incorrect");
            }
            encodedPassword = passwordEncoder.encode(request.newPassword());
        }
        int result = userProfileDAO.updateProfile(userId, request.firstName(), request.lastName(), encodedPassword);
        if (result != SUCCESS) {
            throw new FailedUpdateProfileException("Failed to update profile");
        }
        return new UpdateProfileResponse("Profile updated successfully!");


    }

}
