package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.OrderStatus;
import com.desafio.agendamentos.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id", nullable = false)
    private Mechanic mechanic;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}