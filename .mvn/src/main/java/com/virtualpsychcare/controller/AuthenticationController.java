package com.virtualpsychcare.controller;

import com.virtualpsychcare.dto.AuthRegisterUserRequest;
import com.virtualpsychcare.dto.AuthLoginRequest;
import com.virtualpsychcare.dto.AuthResponse;
import com.virtualpsychcare.exception.ExpiredJwtException;
import com.virtualpsychcare.exception.UserAlreadyExistsException;
import com.virtualpsychcare.service.implementation.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);
    private final UserDetailServiceImpl userDetailService;

    public AuthenticationController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        AuthResponse authResponse = userDetailService.loginUser(userRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthRegisterUserRequest registerUserRequest) {
        AuthResponse registeredUser = userDetailService.registerUser(registerUserRequest);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
