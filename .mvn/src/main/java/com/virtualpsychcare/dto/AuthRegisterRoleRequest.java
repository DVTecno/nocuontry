package com.virtualpsychcare.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
public record AuthRegisterRoleRequest(@Size(max = 1, message  = "Only one role can be assigned.") List<String> roleListName ) {
}
