package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.address.AddressRequest;
import com.desafio.agendamentos.controllers.dtos.address.AddressResponse;
import com.desafio.agendamentos.services.address.IAddressService;
import com.desafio.agendamentos.services.exceptions.AddressNotFoundException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CUSTOMER')")
public class AddressController {

    private final IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @PutMapping("/customer/{customerId}")
    @Operation(summary = "Atualizar endereço", description = "Atualizar o endereço de um cliente")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do endereço atualizado")
    public AddressResponse updateCustomerAddress(
            @PathVariable @Min(1) Long customerId,
            @RequestBody AddressRequest addressRequest
    ) throws CustomerNotFoundException, AddressNotFoundException {
        var updatedAddress = addressService.updateAddress(customerId, addressRequest.toEntity());

        return AddressResponse.fromEntity(updatedAddress);
    }
}