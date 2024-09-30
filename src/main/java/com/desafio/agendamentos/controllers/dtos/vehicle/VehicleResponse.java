package com.desafio.agendamentos.controllers.dtos.vehicle;

import com.desafio.agendamentos.controllers.dtos.customer.CustomerVehicleResponse;
import com.desafio.agendamentos.entities.Vehicle;

public record VehicleResponse(
     Long id,
     String licensePlate,
     String model,
     String manufacturer,
     Integer vehicleYear,
     CustomerVehicleResponse customer
) {
    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return new VehicleResponse(
             vehicle.getId(),
             vehicle.getLicensePlate(),
             vehicle.getModel(),
             vehicle.getManufacturer(),
             vehicle.getVehicleYear(),
             CustomerVehicleResponse.fromEntity(vehicle.getCustomer())
        );
    }
}
