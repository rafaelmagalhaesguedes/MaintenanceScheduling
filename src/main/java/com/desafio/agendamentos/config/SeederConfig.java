package com.desafio.agendamentos.config;

import com.desafio.agendamentos.entities.Admin;
import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.enums.Role;
import com.desafio.agendamentos.repositories.UserRepository;
import com.desafio.agendamentos.services.admin.AdminServiceImpl;
import com.desafio.agendamentos.services.exceptions.UserExistsException;
import com.desafio.agendamentos.services.manager.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SeederConfig {

    @Value("${api.security.admin.name}")
    private String adminName;

    @Value("${api.security.admin.email}")
    private String adminEmail;

    @Value("${api.security.admin.secret}")
    private String adminPassword;

    @Value("${api.security.manager.name}")
    private String managerName;

    @Value("${api.security.manager.email}")
    private String managerEmail;

    @Value("${api.security.manager.secret}")
    private String managerPassword;

    private final UserRepository userRepository;
    private final AdminServiceImpl adminService;
    private final ManagerServiceImpl managerService;

    @Autowired
    public SeederConfig(UserRepository userRepository, AdminServiceImpl adminService, ManagerServiceImpl managerService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.managerService = managerService;
    }

    @PostConstruct
    public void seedDatabase() {
        adminUser();
        managerUser();
    }

    private void adminUser() throws UserExistsException {
        var existingAdmin = userRepository.existsByEmail(adminEmail);

        if (!existingAdmin) {
            var admin = Admin.builder()
                    .name(this.adminName)
                    .email(this.adminEmail)
                    .password(this.adminPassword)
                    .role(Role.ADMIN)
                    .build();

            adminService.create(admin);
        }
    }

    private void managerUser() throws UserExistsException {
        var existingManager = userRepository.existsByEmail(managerEmail);

        if (!existingManager) {
            var manager = Manager.managerBuilder()
                    .name(this.managerName)
                    .email(this.managerEmail)
                    .password(this.managerPassword)
                    .role(Role.MANAGER)
                    .build();

            managerService.create(manager);
        }
    }
}