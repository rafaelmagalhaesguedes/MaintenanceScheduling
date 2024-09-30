package com.desafio.agendamentos.controllers.dtos.customer;

import com.desafio.agendamentos.controllers.dtos.vehicle.VehicleScheduleResponse;
import com.desafio.agendamentos.entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerScheduleResponse(
      Long id,
      String name,
      String email,
      String numberPhone,
      List<VehicleScheduleResponse> vehicles
) {
    public static CustomerScheduleResponse fromEntity(Customer customer) {
        return new CustomerScheduleResponse(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getNumberPhone(),
            customer.getVehicles().stream()
                 .map(VehicleScheduleResponse::fromEntity)
                 .collect(Collectors.toList())
        );
    }
}
