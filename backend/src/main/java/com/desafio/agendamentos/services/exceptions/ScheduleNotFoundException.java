package com.desafio.agendamentos.services.exceptions;

public class ScheduleNotFoundException extends NotFoundException {
    public ScheduleNotFoundException() {
        super("Agendamento não encontrado.");
    }
}
