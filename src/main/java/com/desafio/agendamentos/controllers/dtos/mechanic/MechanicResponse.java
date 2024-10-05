package com.desafio.agendamentos.controllers.dtos.mechanic;

import com.desafio.agendamentos.entities.Mechanic;

public record MechanicResponse(
        Long id,
        String name,
        String email,
        String numberPhone,
        String specialty,
        String register
) {
    public static MechanicResponse fromEntity(Mechanic mechanic) {
        return new MechanicResponse(
                mechanic.getId(),
                mechanic.getName(),
                mechanic.getEmail(),
                mechanic.getNumberPhone(),
                mechanic.getSpecialty(),
                mechanic.getRegister()
        );
    }
}
