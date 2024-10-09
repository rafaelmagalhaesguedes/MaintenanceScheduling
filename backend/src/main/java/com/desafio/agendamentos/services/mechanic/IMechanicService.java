package com.desafio.agendamentos.services.mechanic;

import com.desafio.agendamentos.entities.Mechanic;
import com.desafio.agendamentos.services.exceptions.MechanicNotFoundException;

import java.util.List;

/**
 * Interface para o serviço de mecânicos.
 * Define métodos para criar, encontrar, atualizar e deletar mecânicos.
 */
public interface IMechanicService {

    /**
     * Cria um novo mecânico.
     *
     * @param mechanic o mecânico a ser criado
     * @return o mecânico criado
     */
    Mechanic create(Mechanic mechanic);

    /**
     * Encontra um mecânico pelo ID.
     *
     * @param mechanicId o ID do mecânico
     * @return o mecânico encontrado
     * @throws MechanicNotFoundException se o mecânico não for encontrado
     */
    Mechanic findById(Long mechanicId) throws MechanicNotFoundException;

    /**
     * Atualiza um mecânico existente.
     *
     * @param mechanicId o ID do mecânico
     * @param mechanic os detalhes do mecânico a serem atualizados
     * @throws MechanicNotFoundException se o mecânico não for encontrado
     */
    void update(Long mechanicId, Mechanic mechanic) throws MechanicNotFoundException;

    /**
     * Encontra todos os mecânicos.
     *
     * @return uma lista de todos os mecânicos
     */
    List<Mechanic> findAll();

    /**
     * Deleta um mecânico pelo ID.
     *
     * @param mechanicId o ID do mecânico
     * @throws MechanicNotFoundException se o mecânico não for encontrado
     */
    void delete(Long mechanicId) throws MechanicNotFoundException;
}