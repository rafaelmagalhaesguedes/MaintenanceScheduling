package com.desafio.agendamentos.controllers.dtos.schedule;

import com.desafio.agendamentos.controllers.dtos.customer.CustomerScheduleResponse;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.ScheduleStatus;

import java.time.LocalDateTime;

public record ScheduleCustomerResponse(
     Long id,
     LocalDateTime dateSchedule,
     String descriptionService,
     ScheduleStatus scheduleStatus,
     CustomerScheduleResponse customer
) {
    public static ScheduleCustomerResponse fromEntity(Schedule schedule) {
        return new ScheduleCustomerResponse(
             schedule.getId(),
             schedule.getDateSchedule(),
             schedule.getDescriptionService(),
             schedule.getScheduleStatus(),
             CustomerScheduleResponse.fromEntity(schedule.getCustomer())
        );
    }
}
