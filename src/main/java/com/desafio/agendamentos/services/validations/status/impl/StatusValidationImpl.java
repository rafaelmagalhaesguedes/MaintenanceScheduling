package com.desafio.agendamentos.services.validations.status.impl;

import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;
import com.desafio.agendamentos.services.validations.status.IStatusValidationStrategy;

public class StatusValidationImpl implements IStatusValidationStrategy {
    @Override
    public void validate(Status status) throws StatusValidateException {
        if (status == null || !isValidStatus(status)) {
            throw new StatusValidateException("Invalid status value: " + status);
        }
    }

    private static boolean isValidStatus(Status status) {
        for (Status s : Status.values()) {
            if (s == status) {
                return true;
            }
        }
        return false;
    }
}