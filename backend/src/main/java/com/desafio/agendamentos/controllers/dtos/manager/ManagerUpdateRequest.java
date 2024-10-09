package com.desafio.agendamentos.controllers.dtos.manager;

import com.desafio.agendamentos.entities.Manager;
import jakarta.validation.constraints.*;

public record ManagerUpdateRequest(
        @Size(min = 2, max = 100, message = "Name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Name must contain only alphabetic characters and spaces")
        String name,

        @Email(message = "Email should be valid")
        String email,

        @Positive(message = "The register number must be a positive integer.")
        String register,

        @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
        String department,

        @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters")
        String location
) {
    public Manager toManager() {
        return Manager.managerBuilder()
                .name(name)
                .email(email)
                .register(register)
                .department(department)
                .location(location)
                .build();
    }
}
