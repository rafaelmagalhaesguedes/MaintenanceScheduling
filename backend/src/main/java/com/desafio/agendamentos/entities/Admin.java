package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
    private Boolean isActive = true;
    private Boolean isDeleted = false;

    @Builder
    public Admin(Long id, String name, String email, String password, UserRole userRole, Boolean isActive, Boolean isDeleted) {
        super(id, name, email, password, userRole);
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }
}
