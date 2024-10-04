package com.desafio.agendamentos.services.validations.vehicle;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;

public interface IVehicleValidationStrategy {
    void validate(Vehicle vehicle) throws VehicleValidateException;
}