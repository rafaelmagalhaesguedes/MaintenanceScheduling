package com.desafio.agendamentos.services.validations.schedule;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleValidator {
    private final List<IScheduleValidationStrategy> strategies = new ArrayList<>();

    public void addStrategy(IScheduleValidationStrategy strategy) {
        strategies.add(strategy);
    }

    public void validate(LocalDateTime dateSchedule) throws SchedulingDateException {
        for (IScheduleValidationStrategy strategy : strategies) {
            strategy.validate(dateSchedule);
        }
    }
}