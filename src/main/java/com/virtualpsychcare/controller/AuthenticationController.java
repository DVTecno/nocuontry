package com.virtualpsychcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualpsychcare.dto.AuthRegisterUserRequest;
import com.virtualpsychcare.dto.AuthLoginRequest;
import com.virtualpsychcare.dto.AuthResponse;
import com.virtualpsychcare.exception.ExpiredJwtException;
import com.virtualpsychcare.exception.UserAlreadyExistsException;
import com.virtualpsychcare.service.implementation.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@RequestParam("user") String userJson, @RequestParam MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthRegisterUserRequest registerUserRequest = null;
        try {
            registerUserRequest = objectMapper.readValue(userJson, AuthRegisterUserRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        AuthResponse registeredUser = userDetailService.registerUser(registerUserRequest, file);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
