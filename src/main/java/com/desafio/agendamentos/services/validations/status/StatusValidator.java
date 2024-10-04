package com.desafio.agendamentos.services.validations.status;

import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;

import java.util.ArrayList;
import java.util.List;

public class StatusValidator {
    private final List<IStatusValidationStrategy> strategies = new ArrayList<>();

    public void addStrategy(IStatusValidationStrategy strategy) {
        strategies.add(strategy);
    }

    public void validate(Status status) throws StatusValidateException {
        for (IStatusValidationStrategy strategy : strategies) {
            strategy.validate(status);
        }
    }
}