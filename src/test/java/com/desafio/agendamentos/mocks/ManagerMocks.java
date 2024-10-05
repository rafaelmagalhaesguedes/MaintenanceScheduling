package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.enums.Role;

public class ManagerMocks {

    public static final Manager MANAGER = Manager.managerBuilder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .password("password")
            .role(Role.MANAGER)
            .register("123456")
            .department("IT")
            .location("New York")
            .isActive(true)
            .isDelete(false)
            .build();

    public static final Manager INACTIVE_MANAGER = Manager.managerBuilder()
            .id(2L)
            .name("Jane Doe")
            .email("jane.doe@example.com")
            .password("password")
            .role(Role.MANAGER)
            .register("654321")
            .department("HR")
            .location("Los Angeles")
            .isActive(false)
            .isDelete(false)
            .build();

    public static final Manager DELETED_MANAGER = Manager.managerBuilder()
            .id(3L)
            .name("Jim Beam")
            .email("jim.beam@example.com")
            .password("password")
            .role(Role.MANAGER)
            .register("789012")
            .department("Finance")
            .location("Chicago")
            .isActive(true)
            .isDelete(true)
            .build();
}