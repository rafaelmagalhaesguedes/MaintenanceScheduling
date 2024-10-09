package com.desafio.agendamentos.services.validations.vehicle;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;
import com.desafio.agendamentos.services.validations.vehicle.impl.VehicleLicensePlateValidation;
import com.desafio.agendamentos.services.validations.vehicle.impl.VehicleYearValidation;

public class VehicleValidation {

    public static void vehicleCreationValidate(Vehicle vehicle) throws VehicleValidateException {
        var validator = new VehicleValidator();

        validator.addStrategy(new VehicleLicensePlateValidation());
        validator.addStrategy(new VehicleYearValidation());

        validator.validate(vehicle);
    }
}
