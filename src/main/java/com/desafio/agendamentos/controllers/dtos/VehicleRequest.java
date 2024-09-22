package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Vehicle;

public record VehicleRequest(
    String licensePlate,
    String model,
    String make,
    Integer year
) {
    public Vehicle toEntity() {
        return new Vehicle(licensePlate, model, make, year);
    }
}
