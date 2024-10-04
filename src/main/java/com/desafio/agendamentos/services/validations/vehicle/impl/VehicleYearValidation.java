package com.desafio.agendamentos.services.validations.vehicle.impl;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;
import com.desafio.agendamentos.services.validations.vehicle.IVehicleValidationStrategy;

import java.time.Year;

public class VehicleYearValidation implements IVehicleValidationStrategy {
    private static final Integer VEHICLE_YEAR = 1886;

    @Override
    public void validate(Vehicle vehicle) throws VehicleValidateException {
        var year = vehicle.getVehicleYear();
        var currentYear = Year.now().getValue();

        if (year == null || year < VEHICLE_YEAR || year > currentYear) {
            throw new VehicleValidateException("Invalid vehicle year.");
        }
    }
}