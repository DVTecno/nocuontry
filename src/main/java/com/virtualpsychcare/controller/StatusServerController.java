package com.virtualpsychcare.controller;

import com.virtualpsychcare.utility.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/status")
public class StatusServerController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> status() {
        return new ResponseEntity<>(ResponseUtils.createResponse("Server is running!"), HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Object>> statusAuth() {
        return new ResponseEntity<>(ResponseUtils.createResponse("Server is running Auth!"), HttpStatus.OK);
    }
}
