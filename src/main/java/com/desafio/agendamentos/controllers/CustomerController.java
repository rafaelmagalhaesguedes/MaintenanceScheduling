package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.*;
import com.desafio.agendamentos.services.AddressService;
import com.desafio.agendamentos.services.CustomerService;
import com.desafio.agendamentos.services.ScheduleService;
import com.desafio.agendamentos.services.VehicleService;
import com.desafio.agendamentos.services.exceptions.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final ScheduleService scheduleService;
    private final AddressService addressService;

    @Autowired
    public CustomerController(CustomerService customerService, VehicleService vehicleService, ScheduleService scheduleService, AddressService addressService) {
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.scheduleService = scheduleService;
        this.addressService = addressService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Valid CustomerRequest request) throws CustomerExistsException {
        return CustomerResponse.fromEntity(
                customerService.create(request.toEntity()));
    }

    @GetMapping("/{customerId}")
    public CustomerResponse findCustomerById(@PathVariable Long customerId) throws CustomerNotFoundException {
        return CustomerResponse.fromEntity(
                customerService.findById(customerId)
        );
    }

    @PutMapping("/{customerId}")
    public CustomerResponse updateCustomer(@PathVariable Long customerId, @RequestBody @Valid CustomerRequest request) throws CustomerNotFoundException {
        return CustomerResponse.fromEntity(
                customerService.update(customerId, request.toEntity())
        );
    }

    @DeleteMapping("/{customerId}")
    public CustomerResponse deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        return CustomerResponse.fromEntity(
                customerService.delete(customerId)
        );
    }

    @GetMapping
    public List<CustomerResponse> listCustomers(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return customerService.list(pageNumber, pageSize)
                .stream()
                .map(CustomerResponse::fromEntity)
                .toList();
    }

    @PutMapping("/{customerId}/address/{addressId}")
    public AddressResponse updateCepAddress(@PathVariable Long customerId,
                                            @PathVariable Long addressId,
                                            @RequestBody AddressRequest request) throws CustomerNotFoundException, AddressNotFoundException, CepNotFoundException {
        return AddressResponse.fromEntity(
                addressService.updateCep(customerId, addressId, request.toEntity())
        );
    }

    @PostMapping("/{customerId}/vehicle")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse createVehicle(@PathVariable Long customerId,
                                         @RequestBody VehicleRequest request) throws CustomerNotFoundException{
        return VehicleResponse.fromEntity(
                vehicleService.create(customerId, request.toEntity()));
    }

    @GetMapping("/{customerId}/vehicle")
    public List<VehicleResponse> findVehicle(@PathVariable Long customerId) throws VehicleNotFoundException {
        return vehicleService.findByCustomerId(customerId).stream()
                .map(VehicleResponse::fromEntity)
                .toList();
    }

    @PutMapping("/{customerId}/vehicle/{vehicleId}")
    public VehicleResponse updateVehicle(@PathVariable Long customerId,
                                         @PathVariable Long vehicleId,
                                         @RequestBody VehicleRequest request) throws CustomerNotFoundException, VehicleNotFoundException {
        return VehicleResponse.fromEntity(
                vehicleService.update(customerId, vehicleId, request.toEntity()));
    }

    @DeleteMapping("/{customerId}/vehicle/{vehicleId}")
    public VehicleResponse deleteVehicle(@PathVariable Long customerId,
                                         @PathVariable Long vehicleId) throws CustomerNotFoundException, VehicleNotFoundException {
        return VehicleResponse.fromEntity(
                vehicleService.delete(customerId, vehicleId)
        );
    }

    @PostMapping("/{customerId}/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse createSchedule(@PathVariable Long customerId,
                                           @RequestBody ScheduleRequest request) throws CustomerNotFoundException {
        return ScheduleResponse.fromEntity(
            scheduleService.createSchedule(customerId, request.toEntity())
        );
    }
}
