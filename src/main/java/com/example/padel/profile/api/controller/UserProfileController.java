package com.example.padel.profile.api.controller;

import com.example.padel.profile.api.request.UpdateProfileRequest;
import com.example.padel.profile.api.response.UpdateProfileResponse;
import com.example.padel.profile.api.response.UserProfileResponse;
import com.example.padel.profile.services.GetProfileService;
import com.example.padel.profile.services.UpdateProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final UpdateProfileService updateProfileService;
    private final GetProfileService getProfileService;

    public UserProfileController(UpdateProfileService updateProfileService, GetProfileService getProfileService) {
        this.updateProfileService = updateProfileService;
        this.getProfileService = getProfileService;
    }

    @PatchMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UpdateProfileResponse updateProfile(
            @Valid  @RequestBody UpdateProfileRequest request,
            HttpServletRequest httpRequest
    ) {
        return updateProfileService.updateProfile(request, httpRequest);

    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse getCurrentUser(HttpServletRequest request) {
        return getProfileService.getProfile(request);
    }
}
