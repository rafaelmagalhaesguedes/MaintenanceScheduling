package com.desafio.agendamentos.controllers.dtos.order;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.entities.Mechanic;
import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OrderRequest(
        @NotNull(message = "Schedule ID is mandatory")
        Long scheduleId,

        @NotNull(message = "Manager ID is mandatory")
        Long managerId,

        @NotNull(message = "Mechanic ID is mandatory")
        Long mechanicId
) {
    public Order toEntity() {
        return Order.builder()
                .schedule(Schedule.builder().id(scheduleId).build())
                .manager(Manager.managerBuilder().id(managerId).build())
                .mechanic(Mechanic.builder().id(mechanicId).build())
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();
    }
}
