package com.desafio.agendamentos.services.order;

import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.enums.OrderStatus;
import com.desafio.agendamentos.services.exceptions.OrderNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface para o serviço de ordens de serviço.
 * Define métodos para criar, encontrar, atualizar, listar e deletar ordens de serviço.
 */
public interface IOrderService {

    /**
     * Cria uma nova ordem de serviço.
     *
     * @param order a ordem de serviço a ser criada
     * @return a ordem de serviço criada
     */
    Order create(Order order);

    /**
     * Encontra uma ordem de serviço pelo ID.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @return a ordem de serviço encontrada
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    Order findById(Long serviceOrderId) throws OrderNotFoundException;

    /**
     * Atualiza uma ordem de serviço existente.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @param order os detalhes da ordem de serviço a serem atualizados
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    void update(Long serviceOrderId, Order order) throws OrderNotFoundException;

    /**
     * Atualiza o status de uma ordem de serviço.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @param status o novo status da ordem de serviço
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    void updateStatus(Long serviceOrderId, OrderStatus status) throws OrderNotFoundException;

    /**
     * Encontra todas as ordens de serviço.
     *
     * @return uma lista de todas as ordens de serviço
     */
    Page<Order> findAll(Pageable pageable);

    /**
     * Deleta uma ordem de serviço pelo ID.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    void delete(Long serviceOrderId) throws OrderNotFoundException;
}