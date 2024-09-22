package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Schedule;

import java.time.LocalDateTime;

public record ScheduleRequest(LocalDateTime dateSchedule, String descriptionService) {
    public Schedule toEntity() {
        return new Schedule(dateSchedule, descriptionService);
    }
}
