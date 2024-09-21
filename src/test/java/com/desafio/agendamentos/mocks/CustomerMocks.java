package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Customer;

public class CustomerMocks {
    public static Customer CUSTOMER = new Customer(
            1L,
            "Customer 01",
            "customer@email.com",
            "11 22222 2222",
            "12345678919");

    public static Customer INVALID_CUSTOMER = new Customer(
            "",
            "",
            "",
            "");

    public static Customer CUSTOMER_UPDATED = new Customer(
            "Customer Update",
            "update@email.com",
            "22 33333 3333",
            "12345678918");

    public static Customer C1 = new Customer(
            "Customer 01",
            "customer@email.com",
            "11 22222 2222",
            "12345678910");

    public static Customer C2 = new Customer(
            "Customer 02",
            "customer2@email.com",
            "11 22222 2222",
            "12345678912");
}
