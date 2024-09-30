package com.desafio.agendamentos.controllers.dtos.schedule;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;

import java.time.LocalDateTime;

public record ScheduleResponse(
     Long id,
     LocalDateTime dateSchedule,
     String descriptionService,
     Status status
) {
    public static ScheduleResponse fromEntity(Schedule schedule) {
        return new ScheduleResponse(
             schedule.getId(),
             schedule.getDateSchedule(),
             schedule.getDescriptionService(),
             schedule.getStatus()
        );
    }
}
