package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Admin;
import com.desafio.agendamentos.enums.Role;

public class AdminMocks {

    public static final Admin ADMIN = Admin.builder()
            .id(1L)
            .name("Admin User")
            .email("admin@example.com")
            .password("password")
            .role(Role.ADMIN)
            .build();

    public static final Admin INACTIVE_ADMIN = Admin.builder()
            .id(2L)
            .name("Inactive Admin")
            .email("inactive_admin@example.com")
            .password("password")
            .role(Role.ADMIN)
            .isActive(false)
            .isDeleted(false)
            .build();

    public static final Admin DELETED_ADMIN = Admin.builder()
            .id(3L)
            .name("Deleted Admin")
            .email("deleted_admin@example.com")
            .password("password")
            .role(Role.ADMIN)
            .isActive(true)
            .isDeleted(true)
            .build();
}
