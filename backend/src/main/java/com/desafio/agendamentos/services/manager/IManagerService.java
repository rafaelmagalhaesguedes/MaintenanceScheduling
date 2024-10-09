package com.desafio.agendamentos.services.manager;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.services.exceptions.ManagerNotFoundException;
import com.desafio.agendamentos.services.exceptions.UserExistsException;

/**
 * Interface para o serviço de gerentes.
 * Define métodos para criar, encontrar, atualizar e deletar gerentes.
 */
public interface IManagerService {

    /**
     * Cria um novo gerente.
     *
     * @param manager o gerente a ser criado
     * @return o gerente criado
     * @throws UserExistsException se o gerente já existir
     */
    Manager create(Manager manager) throws UserExistsException;

    /**
     * Encontra um gerente pelo ID.
     *
     * @param managerId o ID do gerente
     * @return o gerente encontrado
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    Manager findById(Long managerId) throws ManagerNotFoundException;

    /**
     * Atualiza um gerente existente.
     *
     * @param managerId o ID do gerente
     * @param manager os detalhes do gerente a serem atualizados
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    void update(Long managerId, Manager manager) throws ManagerNotFoundException;

    /**
     * Atualiza o scheduleStatus de atividade de um gerente.
     *
     * @param managerId o ID do gerente
     * @param activeStatus o novo scheduleStatus de atividade
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    void updateActiveStatus(Long managerId, boolean activeStatus) throws ManagerNotFoundException;

    /**
     * Realiza a exclusão lógica de um gerente.
     *
     * @param managerId o ID do gerente
     * @throws ManagerNotFoundException se o gerente não for encontrado
     */
    void softDelete(Long managerId) throws ManagerNotFoundException;
}
