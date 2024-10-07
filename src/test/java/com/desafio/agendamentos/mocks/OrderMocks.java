package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Mechanic;
import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderMocks {

    public static final Order ORDER = Order.builder()
            .id(1L)
            .schedule(Schedule.builder().id(1L).build())
            .manager((Manager) Manager.builder().id(1L).build())
            .mechanic(Mechanic.builder().id(1L).build())
            .createdAt(LocalDateTime.now())
            .orderStatus(OrderStatus.PENDING)
            .build();

    public static final Order UPDATED_ORDER = Order.builder()
            .id(1L)
            .schedule(Schedule.builder().id(1L).build())
            .manager((Manager) Manager.builder().id(1L).build())
            .mechanic(Mechanic.builder().id(1L).build())
            .createdAt(LocalDateTime.now())
            .orderStatus(OrderStatus.IN_PROGRESS)
            .build();
}