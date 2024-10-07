package com.desafio.agendamentos.controllers.dtos.schedule;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.ScheduleStatus;

import java.time.LocalDateTime;

public record ScheduleResponse(
     Long id,
     LocalDateTime dateSchedule,
     String descriptionService,
     ScheduleStatus scheduleStatus
) {
    public static ScheduleResponse fromEntity(Schedule schedule) {
        return new ScheduleResponse(
             schedule.getId(),
             schedule.getDateSchedule(),
             schedule.getDescriptionService(),
             schedule.getScheduleStatus()
        );
    }
}
