package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Vehicle;

public class VehicleMocks {
    public static final Vehicle VEHICLE = Vehicle.builder()
            .id(1L)
            .model("Model 1")
            .licensePlate("ABC1A34")
            .manufacturer("Brand 1")
            .year(2020)
            .customer(CustomerMocks.CUSTOMER)
            .build();

    public static final Vehicle VEHICLE_UPDATED = Vehicle.builder()
            .id(1L)
            .model("Fiat 147")
            .licensePlate("GHI9A01")
            .manufacturer("Fiat")
            .year(1977)
            .customer(CustomerMocks.CUSTOMER) // Assuming the customer remains the same
            .build();
}