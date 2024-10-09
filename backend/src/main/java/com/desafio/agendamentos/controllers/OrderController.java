package com.desafio.agendamentos.controllers;

import com.desafio.agendamentos.controllers.dtos.order.OrderRequest;
import com.desafio.agendamentos.controllers.dtos.order.OrderResponse;
import com.desafio.agendamentos.controllers.dtos.order.OrderUpdateRequest;
import com.desafio.agendamentos.controllers.dtos.order.OrderListResponse;
import com.desafio.agendamentos.entities.Order;
import com.desafio.agendamentos.services.exceptions.OrderNotFoundException;
import com.desafio.agendamentos.services.order.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new service order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service order created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict in creating service order")
    })
    public OrderResponse create(@Valid @RequestBody OrderRequest orderRequest) {
        var serviceOrder = orderService.create(orderRequest);

        return OrderResponse.fromEntity(serviceOrder);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get a service order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service order found"),
            @ApiResponse(responseCode = "404", description = "Service order not found")
    })
    public OrderResponse findById(@PathVariable Long orderId) throws OrderNotFoundException {
        var serviceOrder = orderService.findById(orderId);

        return OrderResponse.fromEntity(serviceOrder);
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "Finalize the order and schedule service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Service order and schedule finalize successfully"),
            @ApiResponse(responseCode = "404", description = "Service order not found")
    })
    public OrderListResponse update(
            @PathVariable @Valid @Min(1) Long orderId,
            @RequestBody OrderUpdateRequest request
    ) throws OrderNotFoundException {
        var updatedOrder = orderService.update(orderId, request);

        return OrderListResponse.fromEntity(updatedOrder);
    }

    @GetMapping
    @Operation(summary = "Get all orders with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found")
    })
    public List<OrderListResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.findAll(pageable);

        return orders.stream()
                .map(OrderListResponse::fromEntity)
                .toList();
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete a service order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Service order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Service order not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long orderId) throws OrderNotFoundException {
        orderService.delete(orderId);

        return ResponseEntity.noContent().build();
    }
}

