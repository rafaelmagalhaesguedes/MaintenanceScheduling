package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.enums.OrderStatus;
import com.desafio.agendamentos.repositories.OrderRepository;
import com.desafio.agendamentos.services.exceptions.OrderNotFoundException;
import com.desafio.agendamentos.services.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void createOrder_WithValidData_ReturnsOrder() {
        // Arrange
        var order = new Order();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        // Act
        var result = orderService.create(order);

        // Assert
        assertThat(result).isEqualTo(order);
    }

    @Test
    public void findById_WithValidId_ReturnsOrder() {
        // Arrange
        var order = new Order();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        // Act
        var result = orderService.findById(1L);

        // Assert
        assertThat(result).isEqualTo(order);
    }

    @Test
    public void findById_WithInvalidId_ThrowsServiceOrderNotFoundException() {
        // Arrange
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderService.findById(1L))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    public void updateStatus_WithValidId_UpdatesOrderStatus() {
        // Arrange
        var order = new Order();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        // Act
        orderService.updateStatus(1L, OrderStatus.COMPLETED);

        // Assert
        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);

        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    @Test
    public void findAll_ReturnsPageOfOrders() {
        // Arrange
        var pageable = PageRequest.of(0, 10);
        Page<Order> orders = new PageImpl<>(Collections.singletonList(new Order()));

        when(orderRepository.findAll(pageable))
                .thenReturn(orders);

        // Act
        Page<Order> result = orderService.findAll(pageable);

        // Assert
        assertThat(result).isEqualTo(orders);
    }

    @Test
    public void deleteOrder_WithValidId_DeletesOrder() {
        // Arrange
        var order = new Order();

        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        // Act
        orderService.delete(1L);

        // Assert
        verify(orderRepository, times(1))
                .delete(any(Order.class));
    }

    @Test
    public void deleteOrder_WithInvalidId_ThrowsServiceOrderNotFoundException() {
        // Arrange
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderService.delete(1L))
                .isInstanceOf(OrderNotFoundException.class);
    }
}
