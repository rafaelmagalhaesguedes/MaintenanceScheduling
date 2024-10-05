package com.desafio.agendamentos.controllers.dtos.manager;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.enums.Role;

public record ManagerResponse(
        Long id,
        String name,
        String email,
        String register,
        String department,
        String location,
        Role role
) {
    public static ManagerResponse fromManager(Manager manager) {
        return new ManagerResponse(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getRegister(),
                manager.getDepartment(),
                manager.getLocation(),
                manager.getRole()
        );
    }
}
