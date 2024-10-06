package com.desafio.agendamentos.services.order;

import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.enums.OrderStatus;
import com.desafio.agendamentos.repositories.OrderRepository;
import com.desafio.agendamentos.services.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Cria uma nova ordem de serviço.
     *
     * @param order a ordem de serviço a ser criada
     * @return a ordem de serviço criada
     */
    @Override
    @Transactional
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Encontra uma ordem de serviço pelo ID.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @return a ordem de serviço encontrada
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    @Override
    public Order findById(Long serviceOrderId) throws OrderNotFoundException {
        return orderRepository.findById(serviceOrderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    /**
     * Atualiza uma ordem de serviço existente.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @param order os detalhes da ordem de serviço a serem atualizados
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    @Override
    @Transactional
    public void update(Long serviceOrderId, Order order) throws OrderNotFoundException {
        Order existingOrder = findById(serviceOrderId);

        existingOrder.setStatus(order.getStatus());
        existingOrder.setMechanic(order.getMechanic());

        orderRepository.save(existingOrder);
    }

    /**
     * Atualiza o status de uma ordem de serviço.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @param status o novo status da ordem de serviço
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    @Override
    @Transactional
    public void updateStatus(Long serviceOrderId, OrderStatus status) throws OrderNotFoundException {
        Order order = findById(serviceOrderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    /**
     * Encontra todas as ordens de serviço.
     *
     * @return uma lista de todas as ordens de serviço
     */
    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    /**
     * Deleta uma ordem de serviço pelo ID.
     *
     * @param serviceOrderId o ID da ordem de serviço
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    @Override
    @Transactional
    public void delete(Long serviceOrderId) throws OrderNotFoundException {
        Order order = findById(serviceOrderId);
        orderRepository.delete(order);
    }
}
