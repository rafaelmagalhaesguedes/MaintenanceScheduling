package com.desafio.agendamentos.services.order;

import com.desafio.agendamentos.controllers.dtos.order.OrderRequest;
import com.desafio.agendamentos.controllers.dtos.order.OrderUpdateRequest;
import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.enums.OrderStatus;
import com.desafio.agendamentos.enums.ScheduleStatus;
import com.desafio.agendamentos.repositories.ManagerRepository;
import com.desafio.agendamentos.repositories.MechanicRepository;
import com.desafio.agendamentos.repositories.OrderRepository;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.exceptions.ManagerNotFoundException;
import com.desafio.agendamentos.services.exceptions.MechanicNotFoundException;
import com.desafio.agendamentos.services.exceptions.OrderNotFoundException;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ScheduleRepository scheduleRepository;
    private final ManagerRepository managerRepository;
    private final MechanicRepository mechanicRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ScheduleRepository scheduleRepository, ManagerRepository managerRepository, MechanicRepository mechanicRepository) {
        this.orderRepository = orderRepository;
        this.scheduleRepository = scheduleRepository;
        this.managerRepository = managerRepository;
        this.mechanicRepository = mechanicRepository;
    }

    /**
     * Cria uma nova ordem de serviço.
     *
     * @param orderRequest a ordem de serviço a ser criada
     * @return a ordem de serviço criada
     */
    @Override
    @Transactional
    public Order create(OrderRequest orderRequest) throws ScheduleNotFoundException, ManagerNotFoundException, MechanicNotFoundException {
        var schedule = scheduleRepository.findById(orderRequest.scheduleId())
                .orElseThrow(ScheduleNotFoundException::new);

        var manager = managerRepository.findById(orderRequest.managerId())
                .orElseThrow(ManagerNotFoundException::new);

        var mechanic = mechanicRepository.findById(orderRequest.mechanicId())
                .orElseThrow(MechanicNotFoundException::new);

        var order = Order.builder()
                .schedule(schedule)
                .manager(manager)
                .mechanic(mechanic)
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.IN_PROGRESS)
                .build();

        return orderRepository.save(order);
    }

    /**
     * Encontra uma ordem de serviço pelo ID.
     *
     * @param orderId o ID da ordem de serviço
     * @return a ordem de serviço encontrada
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    @Override
    public Order findById(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    /**
     * Finaliza uma ordem de serviço.
     *
     * @param orderId o ID da ordem de serviço
     * @throws OrderNotFoundException se a ordem de serviço não for encontrada
     */
    @Override
    @Transactional
    public Order update(Long orderId, OrderUpdateRequest request) throws OrderNotFoundException {

        var order = findById(orderId);

        // Atualiza os dados da ordem de serviço
        order.setPaymentType(request.paymentType());
        order.setTotalAmount(request.totalAmount());
        order.getSchedule().setScheduleStatus(ScheduleStatus.REALIZADO);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setFinishedAt(LocalDateTime.now());

        // Salva e finaliza ordem de servico
        return orderRepository.save(order);
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
