package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Customer;

public record CustomerVehicleResponse(
        Long id,
        String name
) {
    public static CustomerVehicleResponse fromEntity(Customer customer) {
        return new CustomerVehicleResponse(
                customer.getId(),
                customer.getName()
        );
    }
}
