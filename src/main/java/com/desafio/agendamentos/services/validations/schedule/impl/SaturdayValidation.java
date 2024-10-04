package com.desafio.agendamentos.services.validations.schedule.impl;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.validations.schedule.IScheduleValidationStrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class SaturdayValidation implements IScheduleValidationStrategy {

    @Override
    public void validate(LocalDateTime dateSchedule) throws SchedulingDateException {
        var day = dateSchedule.getDayOfWeek();
        var hour = dateSchedule.getHour();

        if (day == DayOfWeek.SATURDAY && hour > 12) {
            throw new SchedulingDateException("Não é permitido agendar aos sábados depois das 12:00.");
        }
    }
}