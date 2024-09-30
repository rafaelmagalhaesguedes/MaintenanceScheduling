package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String numberPhone,
        String document,
        AddressResponse address,
        List<VehicleResponse> vehicles,
        List<ScheduleResponse> schedules
) {
    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getNumberPhone(),
            customer.getRawDocument(),
            AddressResponse.fromEntity(customer.getAddress()),
            customer.getVehicles().stream()
                    .map(VehicleResponse::fromEntity)
                    .collect(Collectors.toList()),
            customer.getAppointments().stream()
                    .map(ScheduleResponse::fromEntity)
                    .collect(Collectors.toList())
        );
    }
}
