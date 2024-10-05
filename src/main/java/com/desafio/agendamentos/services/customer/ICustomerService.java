package com.desafio.agendamentos.services.customer;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.services.exceptions.*;

/**
 * Interface para o serviço de cliente.
 * Define métodos para criar, encontrar, atualizar e deletar clientes.
 */
public interface ICustomerService {

    /**
     * Cria um novo cliente.
     *
     * @param customer o cliente a ser criado
     * @return o cliente criado
     * @throws UserExistsException se o cliente já existir
     */
    Customer createCustomer(Customer customer) throws UserExistsException;

    /**
     * Encontra um cliente pelo ID.
     *
     * @param customerId o ID do cliente
     * @return o cliente encontrado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    Customer findCustomerById(Long customerId) throws CustomerNotFoundException;

    /**
     * Atualiza um cliente existente.
     *
     * @param customerId o ID do cliente
     * @param customer os detalhes do cliente a serem atualizados
     * @return o cliente atualizado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    Customer updateCustomer(Long customerId, Customer customer) throws CustomerNotFoundException;

    /**
     * Deleta um cliente existente.
     *
     * @param customerId o ID do cliente
     * @return o cliente deletado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    Customer deleteCustomer(Long customerId) throws CustomerNotFoundException;
}