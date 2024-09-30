package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleCustomerResponse;
import com.desafio.agendamentos.controllers.dtos.status.StatusRequest;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.services.ScheduleService;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduling")
@Validated
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "Buscar Agendamento", description = "Buscar agendamento via ID")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do agendamento encontrado")
    public ScheduleCustomerResponse findScheduleById(@PathVariable @Min(1) Long scheduleId) throws ScheduleNotFoundException {
        return ScheduleCustomerResponse.fromEntity(
                scheduleService.findScheduleById(scheduleId));
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Atualizar Status Agendamento", description = "Atualizar status do agendamento")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do agendamento com status atualizado")
    public ScheduleCustomerResponse updateScheduleStatus(@PathVariable Long scheduleId,
                                                         @RequestBody StatusRequest statusRequest) throws ScheduleNotFoundException, StatusValidateException {
        return ScheduleCustomerResponse.fromEntity(
                scheduleService.updateScheduleStatus(scheduleId, statusRequest.status()));
    }

    @GetMapping
    @Operation(summary = "Listar Agendamentos", description = "Listar agendamentos com paginação")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do agendamento paginados")
    public List<ScheduleCustomerResponse> listSchedulings(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return scheduleService.listSchedulings(page, pageSize)
                .stream()
                .map(ScheduleCustomerResponse::fromEntity)
                .toList();
    }

    @GetMapping("/status")
    @Operation(summary = "Listar Agendamentos Filtrados", description = "Listar agendamentos filtrados por Status com paginação")
    @ApiResponse(responseCode = "200", description = "Retorna os dados do filtro paginados")
    public List<ScheduleCustomerResponse> listSchedulingsByStatus(@RequestParam(name = "status", defaultValue = "PENDENTE") Status status,
                                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return scheduleService.listSchedulingsByStatus(status, page, pageSize)
                .stream()
                .map(ScheduleCustomerResponse::fromEntity)
                .toList();
    }
}
