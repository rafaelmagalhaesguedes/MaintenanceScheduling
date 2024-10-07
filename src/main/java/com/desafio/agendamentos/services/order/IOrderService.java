package com.desafio.agendamentos.services.order;

import com.desafio.agendamentos.controllers.dtos.order.OrderRequest;
import com.desafio.agendamentos.controllers.dtos.order.OrderUpdateRequest;
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
     * @param orderRequest a ordem de serviço a ser criada
     * @return a ordem de serviço criada
     */
    Order create(OrderRequest orderRequest);

    /**
     * Encontra uma ordem de serviço pelo ID.
     *
     * @param orderId o ID da ordem de serviço
     * @return a ordem de serviço encontrada
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    Order findById(Long orderId) throws OrderNotFoundException;

    /**
     * Finaliza uma ordem de serviço.
     *
     * @param orderId o ID da ordem de serviço
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    Order update(Long orderId, OrderUpdateRequest request) throws OrderNotFoundException;

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