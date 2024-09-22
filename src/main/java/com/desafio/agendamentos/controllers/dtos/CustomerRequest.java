package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.entities.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "\\d{11,13}", message = "Phone number should be between 11 and 13 digits")
        String numberPhone,

        @NotBlank(message = "Document is mandatory")
        @CPF(message = "Invalid Document")
        String document,

        @Pattern(regexp = "\\d{8}", message = "CEP should be exactly 8 digits")
        String cep
) {
    public Customer toEntity() {
        Address address = new Address();
        address.setCep(cep);
        return new Customer(name, email, numberPhone, document, address);
    }
}