package com.desafio.agendamentos.services.validations;

import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;

public class StatusValidation {

    public static void validateStatus(Status status) throws StatusValidateException {
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
