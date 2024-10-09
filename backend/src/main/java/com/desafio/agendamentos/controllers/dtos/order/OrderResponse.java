package com.desafio.agendamentos.controllers.dtos.order;

import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String descriptionService,
        LocalDateTime createdAt,
        OrderStatus orderStatus
) {
    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getSchedule().getDescriptionService(),
                order.getCreatedAt(),
                order.getOrderStatus()
        );
    }
}
