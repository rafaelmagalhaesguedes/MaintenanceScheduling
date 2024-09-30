package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerMocks {
    public static final Customer CUSTOMER = Customer.builder()
            .id(1L)
            .name("Customer 01")
            .email("customer@email.com")
            .numberPhone("11222222222")
            .rawDocument("77976101005")
            .vehicles(new ArrayList<>(List.of(VehicleMocks.VEHICLE)))
            .build();

    public static final Customer INVALID_CUSTOMER = Customer.builder()
            .name("")
            .email("")
            .numberPhone("")
            .rawDocument("")
            .vehicles(new ArrayList<>())
            .build();

    public static final Customer CUSTOMER_UPDATED = Customer.builder()
            .name("Customer Update")
            .email("update@email.com")
            .numberPhone("22333333333")
            .rawDocument("12345678918")
            .vehicles(new ArrayList<>(List.of(VehicleMocks.VEHICLE)))
            .build();
}