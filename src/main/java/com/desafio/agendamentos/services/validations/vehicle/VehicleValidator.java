package com.desafio.agendamentos.services.validations.vehicle;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;

import java.util.ArrayList;
import java.util.List;

public class VehicleValidator {
    private final List<IVehicleValidationStrategy> strategies = new ArrayList<>();

    public void addStrategy(IVehicleValidationStrategy strategy) {
        strategies.add(strategy);
    }

    public void validate(Vehicle vehicle) throws VehicleValidateException {
        for (IVehicleValidationStrategy strategy : strategies) {
            strategy.validate(vehicle);
        }
    }
}