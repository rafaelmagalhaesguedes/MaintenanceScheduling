package com.desafio.agendamentos.services.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User already exists");
    }
}
