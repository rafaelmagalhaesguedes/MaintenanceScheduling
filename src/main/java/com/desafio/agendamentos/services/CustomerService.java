package com.desafio.agendamentos.services;

import static com.desafio.agendamentos.services.validations.ScheduleValidation.validateScheduleDate;
import static com.desafio.agendamentos.services.validations.VehicleValidation.vehicleCreationValidate;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.entities.Vehicle;

import com.desafio.agendamentos.enums.Status;

import com.desafio.agendamentos.repositories.AddressRepository;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.repositories.VehicleRepository;

import com.desafio.agendamentos.services.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ScheduleRepository scheduleRepository;
    private final AddressRepository addressRepository;
    private final CepService cepService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, VehicleRepository vehicleRepository, ScheduleRepository scheduleRepository, AddressRepository addressRepository, CepService cepService) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.scheduleRepository = scheduleRepository;
        this.addressRepository = addressRepository;
        this.cepService = cepService;
    }

    /**
     * Cria um novo cliente.
     *
     * @param customer Dados do cliente.
     * @return Dados do cliente criado.
     * @throws CustomerExistsException Se o cliente existir no sistema.
     */
    @Transactional
    public Customer createCustomer(Customer customer) throws CustomerExistsException {
        var customerExists = customerRepository.findByEmail(customer.getEmail());

        if (customerExists.isPresent()) {
            throw new CustomerExistsException();
        }

        if (customer.getAddress() != null && customer.getAddress().getCep() != null) {
            cepService.fillAddress(customer);
        }

        return customerRepository.save(customer);
    }

    /**
     * Busca um cliente por ID.
     *
     * @param customerId Id do cliente.
     * @return Dados do cliente encontrado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    public Customer findCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * Atualiza um cliente.
     *
     * @param customerId Id do cliente.
     * @param customer Dados do cliente.
     * @return Dados do veículo atualizado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Customer updateCustomer(Long customerId, Customer customer) throws CustomerNotFoundException {
        var customerFromDb = findCustomerById(customerId);

        customerFromDb.setName(customer.getName());
        customerFromDb.setEmail(customer.getEmail());
        customerFromDb.setNumberPhone(customer.getNumberPhone());
        customerFromDb.setRawDocument(customer.getRawDocument());

        if (customer.getAddress() != null && customer.getAddress().getCep() != null) {
            cepService.fillAddress(customer);
            customerFromDb.setAddress(customer.getAddress());
        }

        return customerRepository.save(customerFromDb);
    }

    /**
     * Deleta um cliente.
     *
     * @param customerId Id do cliente.
     * @return Dados do veículo deletado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Customer deleteCustomer(Long customerId) throws CustomerNotFoundException {
        var customer = findCustomerById(customerId);

        customerRepository.deleteById(customerId);

        return customer;
    }

    /**
     * Atualiza um endereço associado a um cliente.
     *
     * @param customerId ID do cliente.
     * @param address Dados do endereço.
     * @return Dados do endereço atualizado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     * @throws AddressNotFoundException Se o endereço não for encontrado.
     */
    @Transactional
    public Address updateCustomerAddress(Long customerId, Address address) throws CustomerNotFoundException, AddressNotFoundException {
        var customer = findCustomerById(customerId);
        var addressId = customer.getAddress().getId();
        var addressFromDb = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        addressFromDb.setCep(address.getCep());
        addressFromDb.setStreet(address.getStreet());
        addressFromDb.setNeighborhood(address.getNeighborhood());
        addressFromDb.setCity(address.getCity());
        addressFromDb.setState(address.getState());

        return addressRepository.save(addressFromDb);
    }

    /**
     * Cria um veículo associado a um cliente.
     *
     * @param customerId Id do cliente.
     * @param vehicle Dados do veículo.
     * @return Dados do veículo criado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Vehicle createCustomerVehicle(Long customerId, Vehicle vehicle) throws CustomerNotFoundException, VehicleValidateException {

        // Verifica se o cliente existe
        var customer = findCustomerById(customerId);

        // Valida dados do veículo (placa e ano de fabricação)
        vehicleCreationValidate(vehicle);

        // Víncula o veículo ao cliente
        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    /**
     * Lista os veículos associados a um cliente.
     *
     * @param customerId Id do cliente.
     * @return Lista com o(s) veículo(s) do cliente.
     * @throws VehicleNotFoundException Se o veículo não for encontrado.
     */
    public List<Vehicle> findVehicleByCustomerId(Long customerId) throws VehicleNotFoundException {
        var customer = findCustomerById(customerId);
        return vehicleRepository.findByCustomerId(customer.getId());
    }

    /**
     * Atualiza um veículo associado a um cliente.
     *
     * @param customerId Id do cliente.
     * @param vehicleId Id do veículo.
     * @param vehicleDetails Dados do veículo.
     * @return Dados do veículo atualizado.
     * @throws VehicleNotFoundException Se o veículo não for encontrado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Vehicle updateCustomerVehicle(Long customerId, Long vehicleId, Vehicle vehicleDetails) throws VehicleNotFoundException, CustomerNotFoundException {
        var customer = findCustomerById(customerId);
        Optional<Vehicle> vehicleFromDb = vehicleRepository.findById(vehicleId);

        if (vehicleFromDb.isEmpty()) {
            throw new VehicleNotFoundException();
        }

        var vehicle = vehicleFromDb.get();
        vehicle.setLicensePlate(vehicleDetails.getLicensePlate());
        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setManufacturer(vehicleDetails.getManufacturer());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    /**
     * Deleta um veículo associado a um cliente.
     *
     * @param customerId Id do cliente.
     * @param vehicleId Id do veículo.
     * @return Dados do veículo deletado.
     * @throws VehicleNotFoundException Se o veículo não for encontrado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Vehicle deleteCustomerVehicle(Long customerId, Long vehicleId) throws VehicleNotFoundException, CustomerNotFoundException {
        var customer = findCustomerById(customerId);
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);

        if (optionalVehicle.isEmpty() || !optionalVehicle.get().getCustomer().equals(customer)) {
            throw new VehicleNotFoundException();
        }

        var vehicle = optionalVehicle.get();
        vehicleRepository.deleteById(vehicle.getId());

        return vehicle;
    }

    /**
     * Cria um agendamento associado a um cliente.
     *
     * @param customerId ID do cliente.
     * @param schedule   Dados do agendamento.
     * @return Dados do agendamento criado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Schedule createCustomerSchedule(Long customerId, Schedule schedule) throws CustomerNotFoundException {
        var customer = findCustomerById(customerId);

        // Verifica se o cliente possui pelo menos um veículo cadastrado
        if (customer.getVehicles().isEmpty()) {
            throw new VehicleNotFoundException();
        }

        // Valida a data de agendamento
        validateScheduleDate(schedule.getDateSchedule());

        // Formata data e hora
        var dateSchedule = formatScheduleDate(schedule.getDateSchedule());

        var newSchedule = Schedule.builder()
                .customer(customer)
                .descriptionService(schedule.getDescriptionService())
                .dateSchedule(dateSchedule)
                .status(Status.PENDENTE)
                .build();

        return scheduleRepository.save(newSchedule);
    }

    /**
     * Lista os agendamentos associados a um cliente.
     *
     * @param customerId ID do cliente.
     * @return Lista com os agendamentos.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    public List<Schedule> findScheduleByCustomerId(Long customerId) throws ScheduleNotFoundException {
        return scheduleRepository.findScheduleByCustomerId(customerId)
                .stream()
                .toList();
    }

    /**
     * Formata data do agendamento.
     *
     * @param dateSchedule Data do agendamento.
     * @return Data do agendamento formatada.
     */
    private static LocalDateTime formatScheduleDate(LocalDateTime dateSchedule) {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(dateSchedule.format(formatter), formatter);
    }
}
