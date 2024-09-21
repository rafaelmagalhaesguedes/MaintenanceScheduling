package com.desafio.agendamentos.services.exceptions;

public class CustomerExistsException extends RuntimeException {
    public CustomerExistsException() {
        super("Customer already exists");
    }
}
