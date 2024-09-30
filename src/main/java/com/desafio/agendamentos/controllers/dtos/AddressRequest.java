package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Address;

public record AddressRequest(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
) {
    public Address toEntity() {
        return Address.builder()
            .cep(cep)
            .street(logradouro)
            .neighborhood(bairro)
            .city(localidade)
            .state(uf)
            .build();
    }
}
