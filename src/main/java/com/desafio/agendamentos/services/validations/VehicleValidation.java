package com.desafio.agendamentos.services.validations;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;

import java.time.Year;

public class VehicleValidation {

    // Regex para validação de placas veículares
    private static final String LICENSE_PLATE_PATTERN = "[A-Z]{3}[0-9][A-Z][0-9]{2}";

    // Seta o ano mínimo dos veículos aceitos na loja
    private static final Integer VEHICLE_YEAR = 1886;

    public static void vehicleCreationValidate(Vehicle vehicle) throws VehicleValidateException {
        validateLicensePlate(vehicle.getLicensePlate());
        validateYear(vehicle.getYear());
    }

    private static void validateLicensePlate(String licensePlate) throws VehicleValidateException {
        if (licensePlate == null || !licensePlate.matches(LICENSE_PLATE_PATTERN)) {
            throw new VehicleValidateException("Invalid license plate format");
        }
    }

    private static void validateYear(Integer year) throws VehicleValidateException {
        int currentYear = Year.now().getValue();
        if (year == null || year < VEHICLE_YEAR || year > currentYear) {
            throw new VehicleValidateException("Invalid year");
        }
    }
}
