package com.virtualpsychcare.service.interfaces;

import com.virtualpsychcare.dto.AuthRegisterUserRequest;
import com.virtualpsychcare.dto.AuthResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    AuthResponse registerUser(AuthRegisterUserRequest registerUserRequest, MultipartFile file);
//    AuthRegisterUserRequest getUserById(Long id);
//    List<AuthRegisterUserRequest> getAllUsers();
//    AuthRegisterUserRequest updateUser(AuthRegisterUserRequest registerUserRequest);
//    void deleteUser(Long id);
}
