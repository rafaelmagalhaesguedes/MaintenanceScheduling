package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VehicleRequest(
    @NotBlank(message = "LicensePlate is mandatory")
    String licensePlate,

    @NotBlank(message = "Model is mandatory")
    @Size(min = 1, max = 100, message = "Model must be between 1 and 100 characters")
    String model,

    @NotBlank(message = "Manufacturer is mandatory")
    @Size(min = 1, max = 100, message = "Manufacturer must be between 1 and 100 characters")
    String manufacturer,

    @NotBlank(message = "Year is mandatory")
    Integer year
) {
    public Vehicle toEntity() {
        return Vehicle.builder()
             .licensePlate(licensePlate.toUpperCase().replaceAll("[\\s-]+", ""))
             .model(model)
             .manufacturer(manufacturer)
             .year(year)
             .build();
    }
}
