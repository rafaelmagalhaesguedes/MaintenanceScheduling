package com.desafio.agendamentos.controllers.dtos.schedule;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.entities.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ScheduleRequest(
     @NotBlank(message = "Date service is mandatory")
     LocalDateTime dateSchedule,

     @NotBlank(message = "Description service is mandatory")
     @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
     String descriptionService,

     @NotNull
     Long vehicleId
) {
    public Schedule toEntity() {
        var vehicle = Vehicle.builder()
                .id(vehicleId)
                .build();

        return Schedule.builder()
             .dateSchedule(dateSchedule)
             .descriptionService(descriptionService)
             .vehicle(vehicle)
             .build();
    }
}
