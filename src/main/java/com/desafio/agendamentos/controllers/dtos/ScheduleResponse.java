package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long id,
        String descriptionService,
        LocalDateTime dateSchedule,
        Status status
) {
    public static ScheduleResponse fromEntity(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getDescriptionService(),
                schedule.getDateSchedule(),
                schedule.getStatus()
        );
    }
}
