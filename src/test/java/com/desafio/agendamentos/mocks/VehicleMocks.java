package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Vehicle;

public class VehicleMocks {
    public static final Vehicle VEHICLE = new Vehicle(
            1L,
            "Model 1",
            "ABC-1234",
            "Brand 1",
            2020,
            CustomerMocks.CUSTOMER
    );

    public static Vehicle VEHICLE_UPDATED = new Vehicle(
            1L,
            "GHI-9101",
            "Fiat 147",
            "Fiat",
            1977
    );
}
