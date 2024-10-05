package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleCustomerResponse;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleRequest;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleResponse;
import com.desafio.agendamentos.controllers.dtos.status.StatusRequest;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;
import com.desafio.agendamentos.services.schedule.IScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/schedules")
public class ScheduleController {

    private final IScheduleService scheduleService;

    public ScheduleController(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
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

    @PutMapping("/{scheduleId}")
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