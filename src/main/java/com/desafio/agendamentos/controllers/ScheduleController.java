package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleCustomerResponse;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleRequest;
import com.desafio.agendamentos.controllers.dtos.schedule.ScheduleResponse;
import com.desafio.agendamentos.controllers.dtos.status.StatusRequest;
import com.desafio.agendamentos.entities.Schedule;
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
@RequestMapping("/schedule")
@PreAuthorize("hasAuthority('MANAGER')")
public class ScheduleController {

    private final IScheduleService scheduleService;

    public ScheduleController(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    @Operation(summary = "Listar agendamentos", description = "Listar os agendamentos com paginação.")
    @ApiResponse(responseCode = "200", description = "Retorna lista de agendamentos paginados")
    public List<ScheduleCustomerResponse> findAllSchedules(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) throws ScheduleNotFoundException {
        List<Schedule> listSchedules = scheduleService
                .findAllSchedules(pageNumber, pageSize);

        return listSchedules.stream()
                .map(ScheduleCustomerResponse::fromEntity)
                .toList();
    }
}