package com.desafio.agendamentos.services.validations.schedule.impl;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.validations.schedule.IScheduleValidationStrategy;

import java.time.LocalDateTime;

public class OneHourAheadValidation implements IScheduleValidationStrategy {

    @Override
    public void validate(LocalDateTime dateSchedule) throws SchedulingDateException {
        var now = LocalDateTime.now();

        if (dateSchedule.isBefore(now.plusHours(1))) {
            throw new SchedulingDateException("O agendamento deve ser feito com pelo menos 1 hora de antecedência.");
        }
    }
}