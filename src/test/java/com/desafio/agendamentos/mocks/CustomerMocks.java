package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Customer;

public class CustomerMocks {
    public static Customer CUSTOMER = new Customer(
            1L,
            "Customer 01",
            "customer@email.com",
            "11222222222",
            "77976101005");

    public static Customer INVALID_CUSTOMER = new Customer(
            "",
            "",
            "",
            "");

    public static Customer CUSTOMER_UPDATED = new Customer(
            "Customer Update",
            "update@email.com",
            "22333333333",
            "12345678918");
}
