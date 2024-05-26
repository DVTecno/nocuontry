package com.virtualpsychcare.dto;

import com.virtualpsychcare.entities.Imagen;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthRegisterUserRequest(@NotBlank String username, @NotBlank String password, @Valid AuthRegisterRoleRequest roleRequest) {
}
