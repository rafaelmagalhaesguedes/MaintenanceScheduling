package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.entities.Vehicle;

import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String numberPhone,
        String document,
        Address address,
        List<Vehicle> vehicles,
        List<Schedule> schedules
) {
    public static CustomerResponse fromEntity(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getNumberPhone(),
                customer.getRawDocument(),
                customer.getAddress(),
                customer.getVehicles().stream().toList(),
                customer.getAppointments().stream().toList()
        );
    }
}
