package com.desafio.agendamentos.services.admin;

import com.desafio.agendamentos.entities.Admin;
import com.desafio.agendamentos.services.exceptions.UserExistsException;

/**
 * Interface para o serviço de administração.
 * Define métodos para criar, encontrar, atualizar e deletar administradores.
 */
public interface IAdminService {

    /**
     * Cria um novo administrador.
     *
     * @param admin o administrador a ser criado
     * @throws UserExistsException se o administrador já existir
     */
    void create(Admin admin) throws UserExistsException;
}
