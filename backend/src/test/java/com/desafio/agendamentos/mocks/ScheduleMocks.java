package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.ScheduleStatus;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class ScheduleMocks {

    public static final Schedule SCHEDULE = Schedule.builder()
            .id(1L)
            .descriptionService("Service Description")
            .dateSchedule(getNextNonSunday(LocalDateTime.now().plusDays(1)))
            .scheduleStatus(ScheduleStatus.PENDENTE)
            .customer(CustomerMocks.CUSTOMER)
            .build();

    private static LocalDateTime getNextNonSunday(LocalDateTime date) {
        while (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date;
    }
}