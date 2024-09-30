package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.address.AddressRequest;
import com.desafio.agendamentos.controllers.dtos.address.AddressResponse;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerCreationResponse;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerRequest;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerResponse;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleCustomerResponse;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleRequest;
import com.desafio.agendamentos.controllers.dtos.vehicle.VehicleRequest;
import com.desafio.agendamentos.controllers.dtos.vehicle.VehicleResponse;
import com.desafio.agendamentos.services.CustomerService;
import com.desafio.agendamentos.services.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Criar um novo cliente")
    @ApiResponse(responseCode = "201", description = "Retorna os dados do cliente criado")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerCreationResponse createCustomer(@RequestBody @Valid CustomerRequest request) throws CustomerExistsException {
        return CustomerCreationResponse.fromEntity(
                customerService.createCustomer(request.toEntity()));
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Buscar cliente", description = "Buscar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente encontrado")
    public CustomerResponse findCustomerById(@PathVariable @Min(1) Long customerId) throws CustomerNotFoundException {
        return CustomerResponse.fromEntity(
                customerService.findCustomerById(customerId));
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Atualizar cliente", description = "Atualizar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente atualizado")
    public CustomerResponse updateCustomer(@PathVariable @Min(1) Long customerId, @RequestBody @Valid CustomerRequest request) throws CustomerNotFoundException {
        return CustomerResponse.fromEntity(
                customerService.updateCustomer(customerId, request.toEntity()));
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Deletar cliente", description = "Deletar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente deletado")
    public CustomerResponse deleteCustomer(@PathVariable @Min(1) Long customerId) throws CustomerNotFoundException {
        return CustomerResponse.fromEntity(
                customerService.deleteCustomer(customerId));
    }

    @PutMapping("/{customerId}/address")
    @Operation(summary = "Atualizar endereço", description = "Atualizar o endereço de um cliente")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do endereço atualizado")
    public AddressResponse updateCustomerAddress(@PathVariable @Min(1) Long customerId, @RequestBody AddressRequest addressRequest) throws CustomerNotFoundException, AddressNotFoundException {
        return AddressResponse.fromEntity(
                customerService.updateCustomerAddress(customerId, addressRequest.toEntity()));
    }

    @PostMapping("/{customerId}/vehicle")
    @Operation(summary = "Criar veículo", description = "Criar um veículo associado a um cliente")
    @ApiResponse(responseCode = "201", description = "Retorna os dados do veículo criado")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse createCustomerVehicle(@PathVariable @Min(1) Long customerId, @RequestBody VehicleRequest request) throws CustomerNotFoundException, VehicleValidateException {
        return VehicleResponse.fromEntity(
                customerService.createCustomerVehicle(customerId, request.toEntity()));
    }

    @GetMapping("/{customerId}/vehicle")
    @Operation(summary = "Listar veículos", description = "Listar os veículos associados a um cliente")
    @ApiResponse(responseCode = "200", description = "Retorna a lista de veículos")
    public List<VehicleResponse> findCustomerVehicle(@PathVariable Long customerId) throws VehicleNotFoundException {
        return customerService.findVehicleByCustomerId(customerId).stream()
                .map(VehicleResponse::fromEntity)
                .toList();
    }

    @PutMapping("/{customerId}/vehicle/{vehicleId}")
    @Operation(summary = "Atualizar veículo", description = "Atualizar o veículo associado a um cliente")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do veículo atualizado")
    public VehicleResponse updateCustomerVehicle(@PathVariable @Min(1) Long customerId, @PathVariable @Min(1) Long vehicleId, @RequestBody VehicleRequest request) throws CustomerNotFoundException, VehicleNotFoundException {
        return VehicleResponse.fromEntity(
                customerService.updateCustomerVehicle(customerId, vehicleId, request.toEntity()));
    }

    @DeleteMapping("/{customerId}/vehicle/{vehicleId}")
    @Operation(summary = "Deletar veículo", description = "Deletar o veículo associado a um cliente.")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do veículo deletado")
    public VehicleResponse deleteCustomerVehicle(@PathVariable @Min(1) Long customerId, @PathVariable @Min(1) Long vehicleId) throws CustomerNotFoundException, VehicleNotFoundException {
        return VehicleResponse.fromEntity(
                customerService.deleteCustomerVehicle(customerId, vehicleId)
        );
    }

    @PostMapping("/{customerId}/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar agendamento", description = "Criar um agendamento associado a um cliente.")
    @ApiResponse(responseCode = "201", description = "Retorna dados agendamento criado")
    public ScheduleCustomerResponse createCustomerSchedule(@PathVariable @Min(1) Long customerId, @RequestBody ScheduleRequest request) throws CustomerNotFoundException {
        return ScheduleCustomerResponse.fromEntity(
                customerService.createCustomerSchedule(customerId, request.toEntity()));
    }

    @GetMapping("/{customerId}/schedule")
    @Operation(summary = "Listar agendamentos", description = "Listar os agendamentos associados a um cliente.")
    @ApiResponse(responseCode = "200", description = "Retorna lista de agendamentos")
    public List<ScheduleCustomerResponse> findCustomerSchedule(@PathVariable @Min(1) Long customerId) throws ScheduleNotFoundException {
        return customerService.findScheduleByCustomerId(customerId)
                .stream()
                .map(ScheduleCustomerResponse::fromEntity)
                .toList();
    }
}