package com.desafio.agendamentos.services.validations.schedule.impl;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.validations.schedule.IScheduleValidationStrategy;

import java.time.LocalDateTime;

public class PastDateValidation implements IScheduleValidationStrategy {
    @Override
    public void validate(LocalDateTime dateSchedule) throws SchedulingDateException {
        var now = LocalDateTime.now();

        if (dateSchedule.isBefore(now)) {
            throw new SchedulingDateException("A data do agendamento não pode ser anterior à data atual.");
        }
    }
}