package com.desafio.agendamentos.services.validations.schedule.impl;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.validations.schedule.IScheduleValidationStrategy;

import java.time.LocalDateTime;

public class WorkingHoursValidation implements IScheduleValidationStrategy {
    private static final int openingHour = 8;
    private static final int closingHour = 18;

    @Override
    public void validate(LocalDateTime dateSchedule) throws SchedulingDateException {
        var hour = dateSchedule.getHour();

        if (hour < openingHour || hour >= closingHour) {
            throw new SchedulingDateException("O agendamento deve ser feito entre 08:00 e 18:00.");
        }
    }
}