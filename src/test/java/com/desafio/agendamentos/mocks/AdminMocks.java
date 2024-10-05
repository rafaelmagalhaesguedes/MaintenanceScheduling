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
}
