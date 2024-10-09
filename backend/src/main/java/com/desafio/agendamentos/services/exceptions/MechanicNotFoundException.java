package com.desafio.agendamentos.services.exceptions;

public class MechanicNotFoundException extends NotFoundException {
    public MechanicNotFoundException() {
        super("Mechanic not found");
    }
}
