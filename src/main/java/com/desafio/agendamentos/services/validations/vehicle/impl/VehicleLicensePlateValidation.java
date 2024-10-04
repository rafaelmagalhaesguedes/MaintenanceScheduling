package com.desafio.agendamentos.services.validations.vehicle.impl;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;
import com.desafio.agendamentos.services.validations.vehicle.IVehicleValidationStrategy;

public class VehicleLicensePlateValidation implements IVehicleValidationStrategy {

    @Override
    public void validate(Vehicle vehicle) throws VehicleValidateException {
        var plate = vehicle.getLicensePlate();

        if (plate == null || plate.isEmpty() || !plate.matches("^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$")) {
            throw new VehicleValidateException("Invalid vehicle license plate.");
        }
    }
}