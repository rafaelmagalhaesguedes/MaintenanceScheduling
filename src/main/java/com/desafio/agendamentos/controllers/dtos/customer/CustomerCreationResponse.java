package com.desafio.agendamentos.controllers.dtos.customer;

import com.desafio.agendamentos.controllers.dtos.address.AddressResponse;
import com.desafio.agendamentos.entities.Customer;

public record CustomerCreationResponse(
        Long id,
        String name,
        String email,
        String numberPhone,
        String document,
        AddressResponse address
) {
    public static CustomerCreationResponse fromEntity(Customer customer) {
        return new CustomerCreationResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getNumberPhone(),
                customer.getRawDocument(),
                AddressResponse.fromEntity(customer.getAddress())
        );
    }
}
