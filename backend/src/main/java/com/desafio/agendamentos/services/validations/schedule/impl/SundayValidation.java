package com.desafio.agendamentos.services.validations.schedule.impl;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.validations.schedule.IScheduleValidationStrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class SundayValidation implements IScheduleValidationStrategy {

    @Override
    public void validate(LocalDateTime dateSchedule) throws SchedulingDateException {
        var day = dateSchedule.getDayOfWeek();

        if (day == DayOfWeek.SUNDAY) {
            throw new SchedulingDateException("Não é permitido agendar aos domingos.");
        }
    }
}