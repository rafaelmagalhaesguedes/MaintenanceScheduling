package com.desafio.agendamentos.controllers.dtos.status;

import com.desafio.agendamentos.enums.ScheduleStatus;
import jakarta.validation.constraints.NotBlank;

public record StatusRequest(
     @NotBlank(message = "ScheduleStatus is mandatory")
     ScheduleStatus scheduleStatus
) { }
