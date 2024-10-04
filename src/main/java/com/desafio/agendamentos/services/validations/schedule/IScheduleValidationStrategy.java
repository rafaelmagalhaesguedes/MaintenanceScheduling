package com.desafio.agendamentos.services.validations.schedule;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;

import java.time.LocalDateTime;

public interface IScheduleValidationStrategy {
    void validate(LocalDateTime dateSchedule) throws SchedulingDateException;
}