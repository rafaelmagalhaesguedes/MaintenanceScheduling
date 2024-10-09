package com.desafio.agendamentos.controllers.dtos.mechanic;

import com.desafio.agendamentos.entities.Mechanic;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MechanicRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Phone number is mandatory")
        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
        String numberPhone,

        @NotBlank(message = "Specialty is mandatory")
        @Size(min = 2, max = 100, message = "Specialty must be between 2 and 100 characters")
        String specialty,

        @NotBlank(message = "Register is mandatory")
        @Size(min = 5, max = 20, message = "Register must be between 5 and 20 characters")
        String register
) {
    public Mechanic toEntity() {
        return Mechanic.builder()
                .name(name)
                .email(email)
                .numberPhone(numberPhone)
                .specialty(specialty)
                .register(register)
                .build();
    }
}
