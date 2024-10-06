package com.desafio.agendamentos.services.validations;

import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;

public class StatusValidation {

    public static void validateStatus(String status) throws StatusValidateException {
        if (status == null || status.trim().isEmpty()) {
            throw new StatusValidateException("Status cannot be null or empty");
        }

        // Remove espaços em branco e converte para uppercase
        String trimmedStatus = status.trim().toUpperCase();

        // Verifica se é um status válido
        if (!isValidStatus(trimmedStatus)) {
            throw new StatusValidateException("Invalid status value: " + status);
        }
    }

    private static boolean isValidStatus(String status) {
        try {
            Status.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
