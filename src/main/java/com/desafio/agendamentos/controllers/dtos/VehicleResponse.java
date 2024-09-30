package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Vehicle;

public record VehicleResponse(
        Long id,
        String licensePlate,
        String model,
        String manufacturer,
        Integer year,
        CustomerVehicleResponse customer
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getModel(),
                vehicle.getManufacturer(),
                vehicle.getYear(),
                CustomerVehicleResponse.fromEntity(vehicle.getCustomer())
        );
    }
}
