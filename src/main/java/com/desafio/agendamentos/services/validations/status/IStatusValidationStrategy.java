package com.desafio.agendamentos.services.validations.status;

import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;

public interface IStatusValidationStrategy {
    void validate(Status status) throws StatusValidateException;
}