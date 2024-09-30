package com.desafio.agendamentos.controllers.dtos.schedule;

import com.desafio.agendamentos.controllers.dtos.customer.CustomerScheduleResponse;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;

import java.time.LocalDateTime;

public record ScheduleCustomerResponse(
     Long id,
     LocalDateTime dateSchedule,
     String descriptionService,
     Status status,
     CustomerScheduleResponse customer
) {
    public static ScheduleCustomerResponse fromEntity(Schedule schedule) {
        return new ScheduleCustomerResponse(
             schedule.getId(),
             schedule.getDateSchedule(),
             schedule.getDescriptionService(),
             schedule.getStatus(),
             CustomerScheduleResponse.fromEntity(schedule.getCustomer())
        );
    }
}
