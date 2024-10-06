package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.mechanic.MechanicRequest;
import com.desafio.agendamentos.controllers.dtos.mechanic.MechanicResponse;
import com.desafio.agendamentos.services.exceptions.MechanicNotFoundException;
import com.desafio.agendamentos.services.mechanic.IMechanicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mechanic")
@PreAuthorize("hasAuthority('MANAGER')")
public class MechanicController {

    private final IMechanicService mechanicService;

    @Autowired
    public MechanicController(IMechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    /**
     * Cria um novo mecânico.
     *
     * @param mechanicRequest o DTO de requisição do mecânico
     * @return o DTO de resposta do mecânico criado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new mechanic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mechanic created successfully"),
            @ApiResponse(responseCode = "409", description = "Mechanic with the given email already exists")
    })
    public MechanicResponse create(@Valid @RequestBody MechanicRequest mechanicRequest) {
        var mechanic = mechanicService.create(mechanicRequest.toEntity());
        return MechanicResponse.fromEntity(mechanic);
    }

    /**
     * Encontra um mecânico pelo ID.
     *
     * @param mechanicId o ID do mecânico
     * @return o DTO de resposta do mecânico encontrado
     */
    @GetMapping("/{mechanicId}")
    @Operation(summary = "Get a mechanic by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mechanic found"),
            @ApiResponse(responseCode = "404", description = "Mechanic not found")
    })
    public MechanicResponse findById(@PathVariable Long mechanicId) throws MechanicNotFoundException {
        var mechanic = mechanicService.findById(mechanicId);
        return MechanicResponse.fromEntity(mechanic);
    }

    /**
     * Atualiza um mecânico existente.
     *
     * @param mechanicId o ID do mecânico
     * @param mechanicRequest os detalhes do mecânico a serem atualizados
     * @return uma resposta vazia com status 204 (No Content)
     */
    @PutMapping("/{mechanicId}")
    @Operation(summary = "Update mechanic by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mechanic updated successfully"),
            @ApiResponse(responseCode = "404", description = "Mechanic not found")
    })
    public ResponseEntity<Void> update(
            @PathVariable Long mechanicId,
            @Valid @RequestBody MechanicRequest mechanicRequest
    ) throws MechanicNotFoundException {
        mechanicService.update(mechanicId, mechanicRequest.toEntity());
        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta um mecânico pelo ID.
     *
     * @param mechanicId o ID do mecânico
     * @return uma resposta vazia com status 204 (No Content)
     */
    @DeleteMapping("/{mechanicId}")
    @Operation(summary = "Delete a mechanic by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mechanic deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Mechanic not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long mechanicId) throws MechanicNotFoundException {
        mechanicService.delete(mechanicId);
        return ResponseEntity.noContent().build();
    }
}