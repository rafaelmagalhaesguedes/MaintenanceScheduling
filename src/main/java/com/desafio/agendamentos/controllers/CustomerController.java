package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.customer.CustomerCreationResponse;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerRequest;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerResponse;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerUpdateRequest;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleCustomerResponse;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleRequest;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleResponse;
import com.desafio.agendamentos.controllers.dtos.status.StatusRequest;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;
import com.desafio.agendamentos.services.exceptions.UserExistsException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.schedule.IScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    private final ICustomerService customerService;
    private final IScheduleService scheduleService;

    @Autowired
    public CustomerController(ICustomerService customerService, IScheduleService scheduleService) {
        this.customerService = customerService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Criar um novo cliente")
    @ApiResponse(responseCode = "201", description = "Retorna os dados do cliente criado")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerCreationResponse createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) throws UserExistsException {
        var newCustomer = customerService.createCustomer(request.toEntity());

        return CustomerCreationResponse.fromEntity(newCustomer);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Buscar cliente", description = "Buscar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente encontrado")
    public CustomerResponse findCustomerById(
            @PathVariable @Min(1) Long customerId
    ) throws CustomerNotFoundException {
        var customer = customerService.findCustomerById(customerId);

        return CustomerResponse.fromEntity(customer);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Atualizar cliente", description = "Atualizar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente atualizado")
    public CustomerResponse updateCustomer(
            @PathVariable @Min(1) Long customerId,
            @RequestBody @Valid CustomerUpdateRequest request
    ) throws CustomerNotFoundException {
        var customerUpdated = customerService.updateCustomer(customerId, request.toEntity());

        return CustomerResponse.fromEntity(customerUpdated);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Deletar cliente", description = "Deletar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente deletado")
    public CustomerResponse deleteCustomer(
            @PathVariable @Min(1) Long customerId
    ) throws CustomerNotFoundException {
        var deletedCustomer = customerService.deleteCustomer(customerId);

        return CustomerResponse.fromEntity(deletedCustomer);
    }

    @PostMapping("/{customerId}/schedule")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar agendamento", description = "Criar um agendamento associado a um cliente.")
    @ApiResponse(responseCode = "201", description = "Retorna dados agendamento criado")
    public ScheduleCustomerResponse createCustomerSchedule(
            @PathVariable @Min(1) Long customerId,
            @RequestBody ScheduleRequest request
    ) throws CustomerNotFoundException {
        var newSchedule = scheduleService.createSchedule(customerId, request.toEntity());

        return ScheduleCustomerResponse.fromEntity(newSchedule);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Listar agendamentos", description = "Listar os agendamentos associados a um cliente.")
    @ApiResponse(responseCode = "200", description = "Retorna lista de agendamentos")
    public List<ScheduleCustomerResponse> findCustomerSchedule(
            @PathVariable @Min(1) Long customerId
    ) throws ScheduleNotFoundException {
        var listSchedules = scheduleService.findSchedulesByCustomerId(customerId);

        return listSchedules.stream()
                .map(ScheduleCustomerResponse::fromEntity)
                .toList();
    }

    @PutMapping("/{customerId}/schedule/{scheduleId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Cancelar agendamento", description = "Cancelar um agendamento associado a um cliente.")
    @ApiResponse(responseCode = "200", description = "Retorna dados agendamento cancelado")
    public ScheduleResponse cancelCustomerSchedule(
            @PathVariable @Min(1) Long customerId,
            @PathVariable @Min(1) Long scheduleId,
            @RequestBody StatusRequest request
    ) throws StatusValidateException {
        var updatedSchedule = scheduleService.cancelSchedule(customerId, scheduleId, request.status());

        return ScheduleResponse.fromEntity(updatedSchedule);
    }
}