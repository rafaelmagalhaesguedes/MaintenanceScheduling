package com.desafio.agendamentos.config;

import com.desafio.agendamentos.entities.Admin;
import com.desafio.agendamentos.enums.Role;
import com.desafio.agendamentos.repositories.UserRepository;
import com.desafio.agendamentos.services.admin.AdminServiceImpl;
import com.desafio.agendamentos.services.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SeederConfig {

    @Value("${api.security.admin.name}")
    private String name;

    @Value("${api.security.admin.email}")
    private String email;

    @Value("${api.security.admin.secret}")
    private String password;

    private final UserRepository userRepository;
    private final AdminServiceImpl adminService;

    @Autowired
    public SeederConfig(UserRepository userRepository, AdminServiceImpl adminService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
    }

    @PostConstruct
    public void seedDatabase() {
        adminUser();
    }

    private void adminUser() throws UserExistsException {
        var existingAdmin = userRepository.existsByEmail(email);

        if (!existingAdmin) {
            var admin = Admin.builder()
                    .name(this.name)
                    .email(this.email)
                    .password(this.password)
                    .role(Role.ADMIN)
                    .build();

            adminService.create(admin);
        }
    }
}