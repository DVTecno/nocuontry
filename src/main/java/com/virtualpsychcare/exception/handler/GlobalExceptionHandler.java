package com.virtualpsychcare.exception.handler;

import com.virtualpsychcare.exception.ExpiredJwtException;
import com.virtualpsychcare.exception.UserAlreadyExistsException;
import com.virtualpsychcare.exception.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, Object> errorMap = new HashMap<>();

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        errorMap.put("Status", "Error");
        errorMap.put("Message", e.getMessage());
        errorMap.put("Code", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        errorMap.put("Status", "Error");
        errorMap.put("Message", e.getMessage());
        errorMap.put("Code", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        errorMap.put("Status", "Error");
        errorMap.put("Message", e.getMessage());
        errorMap.put("Code", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        errorMap.put("Status", "Error");
        errorMap.put("Message", e.getMessage());
        errorMap.put("Code", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> userNotFound(UserNotFoundException exception) {
        errorMap.put("Status", "Error");
        errorMap.put("Message", exception.getMessage());
        errorMap.put("Code", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> badCredentials(BadCredentialsException exception) {
        errorMap.put("Status", "Error");
        errorMap.put("Message", exception.getMessage());
        errorMap.put("Code", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException e) {
        errorMap.put("Status", "Error");
        errorMap.put("Message",  e.getMessage());
        errorMap.put("Timestamp", LocalDateTime.now());
        errorMap.put("Code", HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);
    }
}