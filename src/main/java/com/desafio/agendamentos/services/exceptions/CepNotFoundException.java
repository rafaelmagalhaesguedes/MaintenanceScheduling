package com.desafio.agendamentos.services.exceptions;

public class CepNotFoundException extends NotFoundException {
    public CepNotFoundException() {
        super("CEP not found");
    }
}
