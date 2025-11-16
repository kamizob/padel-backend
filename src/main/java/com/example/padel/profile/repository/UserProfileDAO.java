package com.example.padel.profile.repository;

public interface UserProfileDAO {
    int updateProfile(String userId, String firstName, String lastName, String encodedPassword);
}
