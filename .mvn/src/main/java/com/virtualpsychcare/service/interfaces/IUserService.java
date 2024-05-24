package com.virtualpsychcare.service.interfaces;

import com.virtualpsychcare.dto.AuthRegisterUserRequest;
import com.virtualpsychcare.dto.AuthResponse;

import java.util.List;

public interface IUserService {
    AuthResponse registerUser(AuthRegisterUserRequest registerUserRequest);
//    AuthRegisterUserRequest getUserById(Long id);
//    List<AuthRegisterUserRequest> getAllUsers();
//    AuthRegisterUserRequest updateUser(AuthRegisterUserRequest registerUserRequest);
//    void deleteUser(Long id);
}
