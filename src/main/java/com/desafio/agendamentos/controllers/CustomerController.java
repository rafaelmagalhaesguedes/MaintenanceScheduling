package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.customer.CustomerCreationResponse;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerRequest;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerResponse;
import com.desafio.agendamentos.controllers.dtos.customer.CustomerUpdateRequest;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Criar um novo cliente")
    @ApiResponse(responseCode = "201", description = "Retorna os dados do cliente criado")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerCreationResponse createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) throws CustomerExistsException {
        var newCustomer = customerService.createCustomer(request.toEntity());

        return CustomerCreationResponse.fromEntity(newCustomer);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Buscar cliente", description = "Buscar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente encontrado")
    public CustomerResponse findCustomerById(
            @PathVariable @Min(1) Long customerId
    ) throws CustomerNotFoundException {
        var customer = customerService.findCustomerById(customerId);

        return CustomerResponse.fromEntity(customer);
    }

    @PutMapping("/{customerId}")
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
    @Operation(summary = "Deletar cliente", description = "Deletar um cliente por ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente deletado")
    public CustomerResponse deleteCustomer(
            @PathVariable @Min(1) Long customerId
    ) throws CustomerNotFoundException {
        var deletedCustomer = customerService.deleteCustomer(customerId);

        return CustomerResponse.fromEntity(deletedCustomer);
    }
}