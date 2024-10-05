package com.desafio.agendamentos.services.manager;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.repositories.ManagerRepository;
import com.desafio.agendamentos.repositories.UserRepository;
import com.desafio.agendamentos.services.exceptions.ManagerNotFoundException;
import com.desafio.agendamentos.services.exceptions.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da interface IManagerService.
 * Fornece métodos para criar, encontrar, atualizar e deletar gerentes.
 */
@Service
public class ManagerServiceImpl implements IManagerService {
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerServiceImpl(UserRepository userRepository, ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Manager create(Manager manager) throws UserExistsException {
        var existingManager = userRepository.existsByEmail(manager.getEmail());

        if (existingManager) {
            throw new UserExistsException();
        }

        var encodedPassword = passwordEncoder.encode(manager.getPassword());
        manager.setPassword(encodedPassword);

        return managerRepository.save(manager);
    }

    @Override
    public Manager findById(Long managerId) throws ManagerNotFoundException {
        return managerRepository.findById(managerId)
                .orElseThrow(ManagerNotFoundException::new);
    }

    @Override
    public void update(Long managerId, Manager manager) throws ManagerNotFoundException {
        var managerFromDb = findById(managerId);

        managerFromDb.setName(manager.getName());
        managerFromDb.setEmail(manager.getEmail());
        managerFromDb.setRegister(manager.getRegister());
        managerFromDb.setDepartment(manager.getDepartment());
        managerFromDb.setLocation(manager.getLocation());

        managerRepository.save(managerFromDb);
    }

    @Override
    public void updateActiveStatus(Long managerId, boolean activeStatus) throws ManagerNotFoundException {
        var manager = findById(managerId);
        manager.setIsActive(activeStatus);

        managerRepository.save(manager);
    }

    @Override
    public void softDelete(Long managerId) throws ManagerNotFoundException {
        var manager = findById(managerId);
        manager.setIsDeleted(true);

        managerRepository.save(manager);
    }
}