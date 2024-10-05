package com.desafio.agendamentos.services.admin;

import com.desafio.agendamentos.entities.Admin;
import com.desafio.agendamentos.repositories.AdminRepository;
import com.desafio.agendamentos.repositories.UserRepository;
import com.desafio.agendamentos.services.exceptions.UserExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da interface IAdminService.
 * Fornece métodos para criar, encontrar, atualizar e deletar administradores.
 */
@Service
public class AdminServiceImpl implements IAdminService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(Admin admin) {
        var existingAdmin = userRepository.existsByEmail(admin.getEmail());

        if (existingAdmin) {
            throw new UserExistsException();
        }

        var encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        adminRepository.save(admin);
    }
}