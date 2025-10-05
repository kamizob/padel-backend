package com.example.padel.authorization.repository;

import com.example.padel.authorization.domain.User;

public interface AuthDAO {
    int signUp(User user);
    User findByEmail(String email);
    int verifyUserByEmail(String email);
}
