package com.desafio.agendamentos.services.validations;

import com.desafio.agendamentos.services.exceptions.SchedulingDateException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Classe para validar as datas de agendamento.
 */
public class SchedulingDateValidation {
    private static final int openingHour = 8;
    private static final int closingHour = 18;

    /**
     * Valida a data do agendamento.
     *
     * @param dateSchedule Data do agendamento.
     * @throws SchedulingDateException Se a data do agendamento for inválida.
     */
    public static void validateScheduleDate(LocalDateTime dateSchedule) throws SchedulingDateException {
        LocalDateTime now = LocalDateTime.now();

        // Verifica se a data é anterior à data atual
        if (dateSchedule.isBefore(now)) {
            throw new SchedulingDateException("A data do agendamento não pode ser anterior à data atual.");
        }

        // Verifica se a data é pelo menos 1 hora no futuro
        if (dateSchedule.isBefore(now.plusHours(1))) {
            throw new SchedulingDateException("O agendamento deve ser feito com pelo menos 1 hora de antecedência.");
        }

        // Verifica se está fora do horário de funcionamento
        if (dateSchedule.getHour() < openingHour || dateSchedule.getHour() >= closingHour) {
            throw new SchedulingDateException("O agendamento deve ser feito entre 08:00 e 18:00.");
        }

        // Verifica se é sábado depois das 12:00
        if (dateSchedule.getDayOfWeek() == DayOfWeek.SATURDAY && dateSchedule.getHour() > 12) {
            throw new SchedulingDateException("Não é permitido agendar aos sábados depois das 12:00.");
        }

        // Verifica se é domingo
        if (dateSchedule.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new SchedulingDateException("Não é permitido agendar aos domingos.");
        }
    }
}
