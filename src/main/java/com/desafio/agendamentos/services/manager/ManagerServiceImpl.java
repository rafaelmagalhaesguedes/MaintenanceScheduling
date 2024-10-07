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

    /**
     * Cria um novo gerente.
     *
     * @param manager o gerente a ser criado
     * @return o gerente criado
     * @throws UserExistsException se o gerente já existir
     */
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

    /**
     * Encontra um gerente pelo ID.
     *
     * @param managerId o ID do gerente
     * @return o gerente encontrado
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    @Override
    public Manager findById(Long managerId) throws ManagerNotFoundException {
        return managerRepository.findById(managerId)
                .orElseThrow(ManagerNotFoundException::new);
    }

    /**
     * Atualiza um gerente existente.
     *
     * @param managerId o ID do gerente
     * @param manager os detalhes do gerente a serem atualizados
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
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

    /**
     * Atualiza o scheduleStatus de atividade de um gerente.
     *
     * @param managerId o ID do gerente
     * @param activeStatus o novo scheduleStatus de atividade
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    @Override
    public void updateActiveStatus(Long managerId, boolean activeStatus) throws ManagerNotFoundException {
        var manager = findById(managerId);
        manager.setIsActive(activeStatus);

        managerRepository.save(manager);
    }

    /**
     * Realiza a exclusão lógica de um gerente.
     *
     * @param managerId o ID do gerente
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    @Override
    public void softDelete(Long managerId) throws ManagerNotFoundException {
        var manager = findById(managerId);
        manager.setIsDeleted(true);

        managerRepository.save(manager);
    }
}