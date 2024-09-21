package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.CustomerRequest;
import com.desafio.agendamentos.controllers.dtos.CustomerResponse;
import com.desafio.agendamentos.services.CustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponse create(@RequestBody @Valid CustomerRequest request) throws CustomerExistsException {
        return CustomerResponse.fromEntity(
                customerService.create(request.toEntity()));
    }

    @GetMapping
    public List<CustomerResponse> list(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return customerService.list(pageNumber, pageSize)
                .stream()
                .map(CustomerResponse::fromEntity)
                .toList();
    }
}
