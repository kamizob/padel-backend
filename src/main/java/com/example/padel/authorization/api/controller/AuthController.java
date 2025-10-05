package com.example.padel.authorization.api.controller;

import com.example.padel.authorization.api.request.LoginRequest;
import com.example.padel.authorization.api.request.SignUpRequest;
import com.example.padel.authorization.api.response.LoginResponse;
import com.example.padel.authorization.api.response.SignUpResponse;
import com.example.padel.authorization.repository.AuthDAO;
import com.example.padel.authorization.services.LoginService;
import com.example.padel.authorization.services.SignUpService;
import com.example.padel.config.VerificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SignUpService signUpService;
    private final LoginService loginService;
    private final VerificationTokenService verificationTokenService;
    private final AuthDAO authDAO;


    public AuthController(SignUpService signUpService, LoginService loginService,
                          VerificationTokenService verificationTokenService, AuthDAO authDAO
                          ) {
        this.signUpService = signUpService;
        this.loginService = loginService;
        this.verificationTokenService = verificationTokenService;
        this.authDAO = authDAO;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signup(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        String email = verificationTokenService.extractEmail(token);
        int updated = authDAO.verifyUserByEmail(email);

        if (updated == 1) {
            return "Email verified successfully!";
        } else {
            return "Invalid or expired token.";
        }
    }



}
