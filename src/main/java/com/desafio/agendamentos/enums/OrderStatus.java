package com.desafio.agendamentos.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}