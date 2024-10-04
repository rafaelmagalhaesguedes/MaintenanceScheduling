package com.desafio.agendamentos.services.validations.status;

import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;
import com.desafio.agendamentos.services.validations.status.impl.StatusValidationImpl;

public class StatusValidation {

    public static void validateStatus(Status status) throws StatusValidateException {
        StatusValidator validator = new StatusValidator();

        validator.addStrategy(new StatusValidationImpl());

        validator.validate(status);
    }
}