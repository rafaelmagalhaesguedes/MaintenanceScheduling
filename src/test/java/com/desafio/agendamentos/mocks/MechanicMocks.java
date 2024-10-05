package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Mechanic;

public class MechanicMocks {

    public static final Mechanic MECHANIC = Mechanic.builder()
            .id(1L)
            .name("John Smith")
            .email("john.smith@example.com")
            .numberPhone("31998822233")
            .specialty("Suspension")
            .register("14728")
            .build();
}
