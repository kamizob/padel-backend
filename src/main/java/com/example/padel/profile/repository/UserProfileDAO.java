package com.example.padel.profile.repository;

import com.example.padel.profile.api.response.UserProfileResponse;

public interface UserProfileDAO {
    int updateProfile(String userId, String firstName, String lastName, String encodedPassword);
    UserProfileResponse findProfileById(String userId);
}
