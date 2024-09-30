package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record StatusRequest(
     @NotBlank(message = "Status is mandatory")
     Status status
) { }
