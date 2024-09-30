package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Vehicle;

public record VehicleScheduleResponse(
     String model,
     String licensePlate
) {
    public static VehicleScheduleResponse fromEntity(Vehicle vehicle) {
        return new VehicleScheduleResponse(
             vehicle.getModel(),
             vehicle.getLicensePlate()
        );
    }
}