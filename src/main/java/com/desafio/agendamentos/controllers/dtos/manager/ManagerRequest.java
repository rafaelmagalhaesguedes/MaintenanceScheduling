package com.desafio.agendamentos.controllers.dtos.manager;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ManagerRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Name must contain only alphabetic characters and spaces")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,

        @NotBlank(message = "Register cannot be blank")
        String register,

        @NotBlank(message = "Department cannot be blank")
        @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
        String department,

        @NotBlank(message = "Location cannot be blank")
        @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters")
        String location
) {
    public Manager toManager() {
        return Manager.managerBuilder()
                .name(name)
                .email(email)
                .password(password)
                .register(register)
                .department(department)
                .location(location)
                .role(Role.MANAGER)
                .build();
    }
}
