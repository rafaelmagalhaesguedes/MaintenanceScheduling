package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.vehicle.VehicleRequest;
import com.desafio.agendamentos.controllers.dtos.vehicle.VehicleResponse;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;
import com.desafio.agendamentos.services.vehicle.IVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CUSTOMER')")
public class VehicleController {

    private final IVehicleService vehicleService;

    @Autowired
    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar veículo", description = "Criar um veículo associado a um cliente")
    @ApiResponse(responseCode = "201", description = "Retorna os dados do veículo criado")
    public VehicleResponse createCustomerVehicle(
            @PathVariable @Min(1) Long customerId,
            @RequestBody VehicleRequest request
    ) throws CustomerNotFoundException, VehicleValidateException {
        var newVehicle = vehicleService.createVehicle(customerId, request.toEntity());

        return VehicleResponse.fromEntity(newVehicle);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Listar veículos", description = "Listar os veículos associados a um cliente")
    @ApiResponse(responseCode = "200", description = "Retorna a lista de veículos")
    public List<VehicleResponse> findCustomerVehicle(
            @PathVariable @Min(1) Long customerId
    ) throws VehicleNotFoundException {
        var listVehicles = vehicleService.findVehiclesByCustomerId(customerId);

        return listVehicles.stream()
                .map(VehicleResponse::fromEntity)
                .toList();
    }

    @PutMapping("/{vehicleId}/customer/{customerId}")
    @Operation(summary = "Atualizar veículo", description = "Atualizar o veículo associado a um cliente")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do veículo atualizado")
    public VehicleResponse updateCustomerVehicle(
            @PathVariable @Min(1) Long vehicleId,
            @PathVariable @Min(1) Long customerId,
            @RequestBody VehicleRequest request
    ) throws CustomerNotFoundException, VehicleNotFoundException {
        var updatedVehicle = vehicleService.updateVehicle(vehicleId, customerId, request.toEntity());

        return VehicleResponse.fromEntity(updatedVehicle);
    }

    @DeleteMapping("/{vehicleId}/customer/{customerId}")
    @Operation(summary = "Deletar veículo", description = "Deletar o veículo associado a um cliente.")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do veículo deletado")
    public VehicleResponse deleteCustomerVehicle(
            @PathVariable @Min(1) Long vehicleId,
            @PathVariable @Min(1) Long customerId
    ) throws CustomerNotFoundException, VehicleNotFoundException {
        var deletedVehicle = vehicleService.deleteVehicle(vehicleId, customerId);

        return VehicleResponse.fromEntity(deletedVehicle);
    }
}