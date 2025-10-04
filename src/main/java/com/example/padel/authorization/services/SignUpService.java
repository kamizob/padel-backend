package com.example.padel.authorization.services;

import com.example.padel.authorization.api.request.SignUpRequest;
import com.example.padel.authorization.api.response.SignUpResponse;
import com.example.padel.authorization.domain.User;
import com.example.padel.authorization.domain.enums.Role;
import com.example.padel.authorization.repository.AuthDAO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignUpService {
    private static final int SUCCESS = 1;
    private final AuthDAO authDAO;

    public SignUpService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String userId = UUID.randomUUID().toString();
        User user = new User(
                userId,
                signUpRequest.email(),
                signUpRequest.password(),
                signUpRequest.firstName(),
                signUpRequest.lastName(),
                Role.USER,
                false
        );
        int result = authDAO.signUp(user);
        if (result != SUCCESS) {
            return new SignUpResponse("Sign up failed");
        } else {
            return new SignUpResponse("Sign up successful");
        }


    }
}
