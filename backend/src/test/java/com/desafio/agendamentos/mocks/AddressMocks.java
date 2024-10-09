package com.desafio.agendamentos.mocks;

import com.desafio.agendamentos.entities.Address;

public class AddressMocks {

    public static final Address ADDRESS = Address.builder()
            .id(1L)
            .cep("12345-678")
            .street("Rua Exemplo")
            .neighborhood("Bairro Exemplo")
            .city("Cidade Exemplo")
            .state("Estado Exemplo")
            .build();

    public static final Address ADDRESS_UPDATED = Address.builder()
            .id(1L)
            .cep("87654-321")
            .street("Rua Atualizada")
            .neighborhood("Bairro Atualizado")
            .city("Cidade Atualizada")
            .state("Estado Atualizado")
            .build();
}