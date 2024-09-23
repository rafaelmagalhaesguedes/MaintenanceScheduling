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

    public static final Vehicle VEHICLE_2 = new Vehicle(
            2L,
            "Model 2",
            "DEF-5678",
            "Brand 2",
            2021,
            CustomerMocks.CUSTOMER
    );

    public static final Vehicle INVALID_VEHICLE = new Vehicle(
            0L,
            "",
            "",
            "",
            1969
    );

    public static Vehicle VEHICLE_UPDATED = new Vehicle(
            1L,
            "GHI-9101",
            "Fiat 147",
            "Fiat",
            1977
    );
}
