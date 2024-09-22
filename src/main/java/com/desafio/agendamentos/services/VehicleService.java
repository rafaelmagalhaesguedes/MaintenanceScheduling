package com.desafio.agendamentos.services;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.repositories.VehicleRepository;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, CustomerService customerService) {
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
    }

    public Vehicle create(Long customerId, Vehicle vehicle) throws CustomerNotFoundException {
        Customer customer = customerService.findById(customerId);

        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> findByCustomerId(Long customerId) throws VehicleNotFoundException {
        Customer customer = customerService.findById(customerId);
        return vehicleRepository.findByCustomerId(customer.getId());
    }

    public Vehicle update(Long customerId, Long vehicleId, Vehicle vehicleDetails) throws VehicleNotFoundException, CustomerNotFoundException {
        Customer customer = customerService.findById(customerId);
        Optional<Vehicle> vehicleFromDb = vehicleRepository.findById(vehicleId);

        if (vehicleFromDb.isEmpty()) {
            throw new VehicleNotFoundException();
        }

        Vehicle vehicle = vehicleFromDb.get();
        vehicle.setLicensePlate(vehicleDetails.getLicensePlate());
        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setMake(vehicleDetails.getMake());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    public Vehicle delete(Long customerId, Long vehicleId) throws VehicleNotFoundException, CustomerNotFoundException {
        Customer customer = customerService.findById(customerId);
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);

        if (optionalVehicle.isEmpty() || !optionalVehicle.get().getCustomer().equals(customer)) {
            throw new VehicleNotFoundException();
        }

        Vehicle vehicle = optionalVehicle.get();
        vehicleRepository.deleteById(vehicle.getId());

        return vehicle;
    }
}
