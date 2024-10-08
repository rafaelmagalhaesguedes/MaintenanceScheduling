package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.UserRole;
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
    public Manager(Long id, String name, String email, String password, UserRole userRole, Boolean isActive, Boolean isDeleted, String register, String department, String location) {
        super(id, name, email, password, userRole, isActive, isDeleted);
        this.register = register;
        this.department = department;
        this.location = location;
    }
}