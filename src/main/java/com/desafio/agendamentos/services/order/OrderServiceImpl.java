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

    @Override
    @Transactional
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long serviceOrderId) throws OrderNotFoundException {
        return orderRepository.findById(serviceOrderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    @Transactional
    public void update(Long serviceOrderId, Order order) throws OrderNotFoundException {
        Order existingOrder = findById(serviceOrderId);

        existingOrder.setStatus(order.getStatus());
        existingOrder.setMechanic(order.getMechanic());

        orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public void updateStatus(Long serviceOrderId, OrderStatus status) throws OrderNotFoundException {
        Order order = findById(serviceOrderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(Long serviceOrderId) throws OrderNotFoundException {
        Order order = findById(serviceOrderId);
        orderRepository.delete(order);
    }
}
