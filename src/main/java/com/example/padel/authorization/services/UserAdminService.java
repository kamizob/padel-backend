package com.example.padel.authorization.services;

import com.example.padel.authorization.api.response.PagedUsersResponse;
import com.example.padel.authorization.api.response.UserSummaryResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.domain.enums.Role;
import com.example.padel.authorization.repository.AuthDAO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminService {
    private static final int SUCCESS = 1;
    AuthDAO authDAO;

    public UserAdminService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public void updateUserRole(String targetUserId, String newRole, Authentication auth) {
        User current = authDAO.findByEmail(auth.getName());
        if (current == null) {
            throw new RuntimeException("Authenticated user not found.");
        }
        if (current.getRole() != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only super admin can change user roles.");
        }
        if (current.getId().equals(targetUserId)) {
            throw new RuntimeException("Super-admin cannot change their own role.");
        }
        User target = authDAO.findById(targetUserId);
        if (target == null) {
            throw new RuntimeException("Target user does not exist.");
        }
        Role roleEnum;
        try {
            roleEnum = Role.valueOf(newRole.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid role. Allowed: USER, ADMIN.");
        }
        if (roleEnum == Role.SUPER_ADMIN) {
            throw new RuntimeException("Only SUPER_ADMIN can change user roles.");
        }
        int updated = authDAO.updateUserRole(targetUserId, roleEnum);
        if (updated != SUCCESS) {
            throw new RuntimeException("Failed to update role.");
        }


    }

    public List<UserSummaryResponse> getAllUsers() {
        return authDAO.findAllUsers().stream()
                .map(u -> new UserSummaryResponse(
                        u.getId(),
                        u.getEmail(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getRole().name(),
                        u.isVerified()
                ))
                .toList();
    }

    public PagedUsersResponse getUsers(int page, int size) {
        int offset = page * size;

        var users = authDAO.findAllPaged(offset, size).stream()
                .map(u -> new UserSummaryResponse(
                        u.getId(),
                        u.getEmail(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getRole().name(),
                        u.isVerified()
                ))
                .toList();

        long total = authDAO.countAllUsers();
        int totalPages = (int) Math.ceil((double) total / size);

        return new PagedUsersResponse(users, page, size, total, totalPages);
    }

}
