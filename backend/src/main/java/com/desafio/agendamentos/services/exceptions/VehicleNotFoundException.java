package com.desafio.agendamentos.services.exceptions;

public class VehicleNotFoundException extends NotFoundException {
    public VehicleNotFoundException() {
        super("Vehicle not found");
    }
}
