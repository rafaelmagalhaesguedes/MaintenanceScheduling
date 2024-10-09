package com.desafio.agendamentos.services.vehicle;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.repositories.VehicleRepository;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;
import com.desafio.agendamentos.services.validations.vehicle.VehicleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação da interface IVehicleService.
 * Fornece métodos para criar, encontrar, atualizar e deletar veículos.
 */
@Service
public class VehicleServiceImpl implements IVehicleService {

    private final VehicleRepository vehicleRepository;
    private final ICustomerService customerService;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, ICustomerService customerService) {
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
    }

    /**
     * Cria um novo veículo para um cliente.
     *
     * @param customerId o ID do cliente
     * @param vehicle o veículo a ser criado
     * @return o veículo criado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws VehicleValidateException se a validação do veículo falhar
     */
    @Override
    @Transactional
    public Vehicle createVehicle(Long customerId, Vehicle vehicle) throws CustomerNotFoundException, VehicleValidateException {
        var customer = customerService.findCustomerById(customerId);

        VehicleValidation.vehicleCreationValidate(vehicle);

        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    /**
     * Encontra veículos pelo ID do cliente.
     *
     * @param customerId o ID do cliente
     * @return uma lista de veículos para o cliente
     * @throws VehicleNotFoundException se nenhum veículo for encontrado para o cliente
     */
    @Override
    public List<Vehicle> findVehiclesByCustomerId(Long customerId) throws VehicleNotFoundException {
        var customer = customerService.findCustomerById(customerId);

        return vehicleRepository.findByCustomerId(customer.getId());
    }

    /**
     * Atualiza um veículo para um cliente.
     *
     * @param customerId o ID do cliente
     * @param vehicleId o ID do veículo
     * @param vehicleDetails os detalhes do veículo a serem atualizados
     * @return o veículo atualizado
     * @throws VehicleNotFoundException se o veículo não for encontrado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    @Override
    @Transactional
    public Vehicle updateVehicle(Long customerId, Long vehicleId, Vehicle vehicleDetails) throws VehicleNotFoundException, CustomerNotFoundException {
        var customer = customerService.findCustomerById(customerId);
        Optional<Vehicle> vehicleFromDb = vehicleRepository.findById(vehicleId);

        if (vehicleFromDb.isEmpty()) {
            throw new VehicleNotFoundException();
        }

        var vehicle = vehicleFromDb.get();
        vehicle.setLicensePlate(vehicleDetails.getLicensePlate());
        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setManufacturer(vehicleDetails.getManufacturer());
        vehicle.setVehicleYear(vehicleDetails.getVehicleYear());
        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    /**
     * Deleta um veículo para um cliente.
     *
     * @param customerId o ID do cliente
     * @param vehicleId o ID do veículo
     * @return o veículo deletado
     * @throws VehicleNotFoundException se o veículo não for encontrado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    @Override
    @Transactional
    public Vehicle deleteVehicle(Long customerId, Long vehicleId) throws VehicleNotFoundException, CustomerNotFoundException {
        var customer = customerService.findCustomerById(customerId);
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);

        if (optionalVehicle.isEmpty() || !optionalVehicle.get().getCustomer().equals(customer)) {
            throw new VehicleNotFoundException();
        }

        var vehicle = optionalVehicle.get();
        vehicleRepository.deleteById(vehicle.getId());

        return vehicle;
    }
}