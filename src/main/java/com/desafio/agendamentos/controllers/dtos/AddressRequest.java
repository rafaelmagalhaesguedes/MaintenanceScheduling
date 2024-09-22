package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Address;

public record AddressRequest(String cep) {
    public Address toEntity() {
        return new Address(cep);
    }
}
