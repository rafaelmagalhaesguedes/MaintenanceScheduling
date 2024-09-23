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
        return new Address(cep, logradouro, bairro, localidade, uf);
    }
}
