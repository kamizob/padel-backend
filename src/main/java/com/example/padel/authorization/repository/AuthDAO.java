package com.example.padel.authorization.repository;

import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.domain.enums.Role;

import java.util.List;

public interface AuthDAO {
    int signUp(User user);

    User findByEmail(String email);

    int verifyUserByEmail(String email);

    User findById(String id);

    int updateUserRole(String userId, Role newRole);

    List<User> findAllUsers();

    List<User> findAllPaged(int offset, int limit);

    long countAllUsers();


}
