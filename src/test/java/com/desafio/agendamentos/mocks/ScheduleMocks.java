package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;

import java.time.LocalDateTime;

public class ScheduleMocks {

    public static final Schedule SCHEDULE = Schedule.builder()
            .id(1L)
            .descriptionService("Service Description")
            .dateSchedule(LocalDateTime.now().plusDays(1))
            .status(Status.PENDENTE)
            .customer(CustomerMocks.CUSTOMER)
            .build();

    public static final Schedule SCHEDULE_UPDATED = Schedule.builder()
            .id(1L)
            .descriptionService("Updated Service Description")
            .dateSchedule(LocalDateTime.now().plusDays(2))
            .status(Status.REALIZADO)
            .customer(CustomerMocks.CUSTOMER)
            .build();

    public static final Schedule SCHEDULE_CANCELLED = Schedule.builder()
            .id(1L)
            .descriptionService("Service Description")
            .dateSchedule(LocalDateTime.now().plusDays(1))
            .status(Status.CANCELADO)
            .customer(CustomerMocks.CUSTOMER)
            .build();
}