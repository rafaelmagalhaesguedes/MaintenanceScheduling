package com.desafio.agendamentos.services.exceptions;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException() {
        super("Address not found");
    }
}
