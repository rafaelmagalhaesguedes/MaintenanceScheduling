package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
    private boolean isActive;
    private boolean isDeleted;

    @Builder
    public Admin(Long id, String name, String email, String password, Role role) {
        super(id, name, email, password, role);
        this.isActive = true;
        this.isDeleted = false;
    }
}
