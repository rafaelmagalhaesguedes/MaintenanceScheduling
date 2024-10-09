package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.enums.UserRole;

public class ManagerMocks {

    public static final Manager MANAGER = Manager.managerBuilder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .password("password")
            .userRole(UserRole.MANAGER)
            .register("123456")
            .department("IT")
            .location("New York")
            .isActive(true)
            .isDeleted(false)
            .build();
}