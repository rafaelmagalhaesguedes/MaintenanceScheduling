package com.desafio.agendamentos.controllers.dtos.order;

import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.enums.OrderStatus;
import com.desafio.agendamentos.enums.PaymentType;

import java.time.LocalDateTime;

public record OrderListResponse(
        Long id,
        String descriptionService,
        PaymentType paymentType,
        Double totalAmount,
        LocalDateTime createdAt,
        LocalDateTime finishedAt,
        OrderStatus orderStatus
) {
    public static OrderListResponse fromEntity(Order order) {
        return new OrderListResponse(
                order.getId(),
                order.getSchedule().getDescriptionService(),
                order.getPaymentType(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                order.getFinishedAt(),
                order.getOrderStatus()
        );
    }
}
