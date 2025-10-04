package com.example.padel.authorization.api.controller;

import com.example.padel.authorization.api.request.LoginRequest;
import com.example.padel.authorization.api.request.SignUpRequest;
import com.example.padel.authorization.api.response.LoginResponse;
import com.example.padel.authorization.api.response.SignUpResponse;
import com.example.padel.authorization.services.LoginService;
import com.example.padel.authorization.services.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final SignUpService signUpService;
    private final LoginService loginService;


    public AuthController(SignUpService signUpService, LoginService loginService) {
        this.signUpService = signUpService;
        this.loginService = loginService;
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


}
