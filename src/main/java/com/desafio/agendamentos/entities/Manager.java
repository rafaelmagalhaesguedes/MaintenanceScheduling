package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("MANAGER")
@Getter
@Setter
@NoArgsConstructor
public class Manager extends Admin {
    private String register;
    private String department;
    private String location;

    @Builder(builderMethodName = "managerBuilder")
    public Manager(Long id, String name, String email, String password, Role role, Boolean isActive, Boolean isDelete, String register, String department, String location) {
        super(id, name, email, password, role, isActive, isDelete);
        this.register = register;
        this.department = department;
        this.location = location;
    }
}