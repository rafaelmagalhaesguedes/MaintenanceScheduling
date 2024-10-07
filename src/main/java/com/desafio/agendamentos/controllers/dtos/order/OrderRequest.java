package com.desafio.agendamentos.controllers.dtos.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
        @NotNull(message = "Schedule ID is mandatory")
        @Positive(message = "Schedule ID must be a positive number")
        Long scheduleId,

        @NotNull(message = "Manager ID is mandatory")
        @Positive(message = "Manager ID must be a positive number")
        Long managerId,

        @NotNull(message = "Mechanic ID is mandatory")
        @Positive(message = "Mechanic ID must be a positive number")
        Long mechanicId
) { }