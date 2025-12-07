package com.example.padel.authorization.api.controller;

import com.example.padel.authorization.api.request.AdminSignUpRequest;
import com.example.padel.authorization.api.request.LoginRequest;
import com.example.padel.authorization.api.request.SignUpRequest;
import com.example.padel.authorization.api.request.UpdateUserRoleRequest;
import com.example.padel.authorization.api.response.LoginResponse;
import com.example.padel.authorization.api.response.PagedUsersResponse;
import com.example.padel.authorization.api.response.SignUpResponse;
import com.example.padel.authorization.api.response.UserSummaryResponse;
import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.authorization.services.LoginService;
import com.example.padel.authorization.services.SignUpService;
import com.example.padel.authorization.services.UserAdminService;
import com.example.padel.config.VerificationTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SignUpService signUpService;
    private final LoginService loginService;
    private final VerificationTokenService verificationTokenService;
    private final AuthDAO authDAO;
    private final UserAdminService userAdminService;


    public AuthController(SignUpService signUpService, LoginService loginService,
                          VerificationTokenService verificationTokenService, AuthDAO authDAO,
                          UserAdminService userAdminService
                          ) {
        this.signUpService = signUpService;
        this.loginService = loginService;
        this.verificationTokenService = verificationTokenService;
        this.authDAO = authDAO;
        this.userAdminService = userAdminService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }
    @PostMapping("/signup/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signupAdmin(@Valid @RequestBody AdminSignUpRequest adminSignUpRequest) {
        return signUpService.signUpAdmin(adminSignUpRequest);
    }
    @PostMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateRole(
            @Valid @RequestBody UpdateUserRoleRequest request,
            Authentication auth
    ) {
        userAdminService.updateUserRole(request.userId(), request.newRole(), auth);
        return ResponseEntity.ok("Role updated successfully.");
    }



    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam("token") String token) {
        try {
            String email = verificationTokenService.extractEmail(token);
            int updated = authDAO.verifyUserByEmail(email);

            if (updated == 1) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("http://localhost:5173/verify-success"))
                        .build();
            } else {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("http://localhost:5173/verify-failed"))
                        .build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://localhost:5173/verify-failed"))
                    .build();
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public PagedUsersResponse getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userAdminService.getUsers(page, size);
    }







}
